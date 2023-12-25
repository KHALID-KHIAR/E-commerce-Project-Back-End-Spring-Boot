package com.kh.EcomercebackEndspringboot.Service;

import com.kh.EcomercebackEndspringboot.Config.CustomUserDetails;
import com.kh.EcomercebackEndspringboot.Entity.DTO.AuthRequest;
import com.kh.EcomercebackEndspringboot.Entity.DTO.ChangePasswordRequest;
import com.kh.EcomercebackEndspringboot.Entity.DTO.ErrorResponse;
import com.kh.EcomercebackEndspringboot.Entity.DTO.GlobalResponse;
import com.kh.EcomercebackEndspringboot.Exception.ResourceNotFoundException;
import com.kh.EcomercebackEndspringboot.Repository.UserRepository;
import com.kh.EcomercebackEndspringboot.Repository.EmailVerificationRepository;
import com.kh.EcomercebackEndspringboot.Entity.User;
import com.kh.EcomercebackEndspringboot.Entity.EmailVerification;
import com.kh.EcomercebackEndspringboot.Service_Int.UserServiceInt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserService implements UserServiceInt {
    private final UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService ;
    private final EmailService emailService;
    private final EmailVerificationRepository emailVerifRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder
            , JwtService jwtService, EmailService emailService, EmailVerificationRepository emailVerificationRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtService=jwtService;
        this.emailService=emailService;
        this.emailVerifRepository=emailVerificationRepository;
    }

    @Override
    public HashMap<String, String> handleLogin(AuthRequest authRequest) throws ResourceNotFoundException {
            User user = searchUserByEmail(authRequest.getEmail());
        if(!user.isActive())
            throw new ResourceNotFoundException("User is disabled Please activate your Account !!!");
        if(verifyLoginUser(authRequest)){
            String token = jwtService.generateJwtToken(user.getEmail(),user.getRole());
            HashMap<String,String> map = new HashMap<>();
            map.put("id",String.valueOf(user.getId()));
            map.put("message","Login Successfully");
            map.put("email",user.getEmail());
            map.put("role",user.getRole());
            map.put("token",token);

            return map ;
        }
        else throw new ResourceNotFoundException("Email not exist !!") ;
    }
    public HashMap<String,Object> handleSignUp(User user) throws Exception{
        User user_created =createUser(user); // save the user to DataBase

        String verification_code = emailService.generateVerificationCode();
        EmailVerification emailVerification = emailVerifRepository.searchByEmail(user.getEmail());
        emailVerification.setVerification_code(verification_code);
        emailVerifRepository.save(emailVerification);
        // Send the email verification
        emailService.sendVerificationMail(user_created,verification_code);

        HashMap<String,Object> map = new HashMap<>();
        map.put("id",user_created.getId());
        map.put("firstname",user_created.getFirstname());
        map.put("lastname",user_created.getLastname());
        map.put("email",user_created.getEmail());
        map.put("role",user_created.getRole());
        map.put("products",user_created.getProducts());
        map.put("password",user_created.getPassword());
        map.put("isActive",user_created.isActive());
        map.put("message","Please Verify Your Email address to activate your account !!!");
        return map ;
    }

    public boolean verifyEmailVerificationCode(String email ,String code_req){
        return emailVerifRepository.searchByEmail(email).getVerification_code().equals(code_req);
    }
    public boolean verifyLoginUser(AuthRequest authRequest) throws ResourceNotFoundException {
        User user = searchUserByEmail(authRequest.getEmail());
        if(user!=null){
            if(passwordEncoder.matches(authRequest.getPassword(), user.getPassword()))
                return true ;
            else throw new ResourceNotFoundException("Incorrect Password !!!");
        }
        else throw new ResourceNotFoundException("Email not exist !!") ;

}
    public void activateUserAccount(String email) throws ResourceNotFoundException {
        User user = searchUserByEmail(email);
        user.setActive(true);
        userRepository.save(user);
    }
    public ResponseEntity handleChangeUserPassword(ChangePasswordRequest changPass) throws ResourceNotFoundException {
        User user = searchUserByEmail(changPass.getEmail());
        if(passwordEncoder.matches(changPass.getPassword(),user.getPassword())){
        user.setPassword(passwordEncoder.encode(changPass.getNewPassword()));
        userRepository.save(user);
            return ResponseEntity.ok().body(
                    new GlobalResponse<>(HttpStatus.OK.toString(),"Password Changed Successfully")
            );
        }
        else{
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(HttpStatus.BAD_REQUEST.toString(),"Incorrect Password !!!")
            );
        }
    }
    // CRUD : C: Create----------------------------------------------------------------------
    public User createUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User user1 = userRepository.save(user);
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setEmail(user.getEmail());
        emailVerification.setVerification_code("");
        emailVerifRepository.save(emailVerification); // test what you already typed just in the constructor
        return user1 ;
    }
    // CRUD : U: Update ----------------------------------------------------------------
    public User updateUser(int id ,User user) throws ResourceNotFoundException {
            User existingUser = userRepository.findById(id).orElse(null) ;
            if(existingUser==null)
            throw new ResourceNotFoundException("User not Found with id:"+id);
        else{
            existingUser.setFirstname(user.getFirstname());
            existingUser.setLastname(user.getLastname());
            existingUser.setEmail(user.getEmail());
            existingUser.setRole(user.getRole());
            existingUser.setProducts(user.getProducts());
            return userRepository.save(existingUser);
        }
    }
    // CRUD : R: Read ------------------------------------------------------------------------
    public List<User> searchAllUsers(){
        return userRepository.findAll();
    }
    public User searchUserById(int id)throws ResourceNotFoundException{
        User user =  userRepository.findById(id).orElse(null);
        if(user==null)
            throw new ResourceNotFoundException("User Not Found with id:"+id);
        else return user ;
    }
    public User searchUserByEmail(String email)throws ResourceNotFoundException{
        User user =  userRepository.findByEmail(email).orElse(null);
        if(user==null)
            throw new ResourceNotFoundException("User Not Found with e-mail: "+email);
        else return user ;
    }
    // CRUD : D: Delete -------------------------------------------------------------------
    public String deleteUser(int id)throws ResourceNotFoundException{
        User user = userRepository.findById(id).orElse(null) ;
        if(user ==null) {
            throw new ResourceNotFoundException("User Not Fount with id: " + id);
        }
        else{
            userRepository.deleteById(id);
            emailVerifRepository.deleteByEmail(user.getEmail());
            return "User with id: "+id+" has been Deleted !!" ;
        }
    }
}
