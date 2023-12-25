package com.kh.EcomercebackEndspringboot.Controller;

import com.kh.EcomercebackEndspringboot.Entity.DTO.AuthRequest;
import com.kh.EcomercebackEndspringboot.Entity.DTO.ChangePasswordRequest;
import com.kh.EcomercebackEndspringboot.Entity.DTO.ErrorResponse;
import com.kh.EcomercebackEndspringboot.Entity.DTO.GlobalResponse;
import com.kh.EcomercebackEndspringboot.Entity.User;
import com.kh.EcomercebackEndspringboot.Exception.ResourceNotFoundException;
import com.kh.EcomercebackEndspringboot.Service.EmailService;
import com.kh.EcomercebackEndspringboot.Service.JwtService;
import com.kh.EcomercebackEndspringboot.Service_Int.UserServiceInt;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Validated
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserServiceInt userServiceInt;
    private final EmailService emailService;
    private final Logger logger= LoggerFactory.getLogger(UserController.class);
    public UserController(UserServiceInt userServiceInt,EmailService emailService){
        this.userServiceInt=userServiceInt ;
        this.emailService=emailService;

    }
//===========================================================================
    @Autowired
    JavaMailSender mailSender ;
    @Autowired
    Configuration configuration ;
    @GetMapping("/verify/zanda")
    public String ZandaZanda()throws Exception{
        MimeMessage mimeMessage= mailSender.createMimeMessage() ;
        MimeMessageHelper mHelper=new MimeMessageHelper(mimeMessage);

        Map<String,String> dataModel = new HashMap<>();
        dataModel.put("link","http://localhost:9090/user/verify?email=Zanda Zanda"+"&code=Zanda Zanda");
        dataModel.put("firstname","Zanda Zanda");

        Template template = configuration.getTemplate("Email_Verification.ftl");
        String template_toString = FreeMarkerTemplateUtils.processTemplateIntoString(template,dataModel);


        mHelper.setSubject("Please Verify your E-mail !");
        mHelper.setTo("zandaZanda@gmail.com");
        mHelper.setFrom("khali12dos@gmail.com");
        mHelper.setText(template_toString,true);

        mailSender.send(mimeMessage);
        return "Mail Has been (zanda Zanda) Sent ╚(•⌂•)╝╚(•⌂•)╝╚(•⌂•)╝" ;
    }
//===========================================================================
    @PostMapping("/login")
    public ResponseEntity<GlobalResponse> loginMethode(@RequestBody @Valid AuthRequest authRequest) throws ResourceNotFoundException {
        HashMap<String,String> map = userServiceInt.handleLogin(authRequest);
        return ResponseEntity.ok().body(
                new GlobalResponse(HttpStatus.OK.toString(),map)
        );

    }
    @PostMapping("/addUser")
    public ResponseEntity<GlobalResponse> createNewUser(@RequestBody @Valid User user) throws Exception {
        HashMap<String,Object> map = userServiceInt.handleSignUp(user);
        return ResponseEntity.ok().body(
                new GlobalResponse<>(HttpStatus.CREATED.toString(),map)
        );
    }
    @GetMapping("/verify")
    public ResponseEntity verifyEmail(@RequestParam @Valid @Size(min = 5) String code
            ,@RequestParam @Valid @Email String email) throws ResourceNotFoundException {
        if(userServiceInt.verifyEmailVerificationCode(email,code)){
            userServiceInt.activateUserAccount(email);
            return ResponseEntity.ok().body(
                    new GlobalResponse<>("200","User has been Verified !")
            );
        }
        else return ResponseEntity.badRequest().body(
                new ErrorResponse("400","Error Verification  !!!")
        );
    }
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<GlobalResponse> updateUser(@PathVariable @Valid int id ,@RequestBody @Valid User user) throws ResourceNotFoundException {
        User result = userServiceInt.updateUser(id,user);
        return ResponseEntity.ok().body(
                new GlobalResponse<>("200 Updated",result)
        );
    }

    @PutMapping("/updatePassword")
    public ResponseEntity updateUserPassword(@RequestBody @Valid ChangePasswordRequest changePass)throws Exception{
        return userServiceInt.handleChangeUserPassword(changePass);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<GlobalResponse<User>> getAllUsers(){
        return ResponseEntity.ok().body(
                new GlobalResponse(HttpStatus.OK.toString(),
                        userServiceInt.searchAllUsers())
        );
    }
    @GetMapping("/getUserById/{id}")
    public ResponseEntity<GlobalResponse> getUserById(@PathVariable @Valid int id)throws ResourceNotFoundException{
        User user = userServiceInt.searchUserById(id);
        return ResponseEntity.ok().body(
                new GlobalResponse(HttpStatus.OK.toString(),user)
        );
    }
    @GetMapping("/getUserByEmail/email={email}")
    public ResponseEntity<GlobalResponse> getUserByEmail(@PathVariable @Email(message = "Please Provide a valid Email address!") String email)throws ResourceNotFoundException{
        User user = userServiceInt.searchUserByEmail(email);
        return ResponseEntity.ok().body(
                new GlobalResponse(HttpStatus.OK.toString(),user)
        );
    }
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<GlobalResponse> deleteUser(@PathVariable @Valid int id)throws ResourceNotFoundException{
        return ResponseEntity.ok().body(
                new GlobalResponse(HttpStatus.OK.toString(),
                        userServiceInt.deleteUser(id))
        );
    }
}
