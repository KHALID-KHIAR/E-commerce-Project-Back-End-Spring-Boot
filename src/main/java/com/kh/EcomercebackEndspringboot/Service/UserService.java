package com.kh.EcomercebackEndspringboot.Service;

import com.kh.EcomercebackEndspringboot.Exception.ResourceNotFoundException;
import com.kh.EcomercebackEndspringboot.Repository.UserRepository;
import com.kh.EcomercebackEndspringboot.Entity.User;
import com.kh.EcomercebackEndspringboot.Service_Int.UserServiceInt;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceInt {
    private final UserRepository userRepository ;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CRUD : C: Create
    public String createUser(User user){
        userRepository.save(user);
        return "User Created Successfully" ;
    }
    // CRUD : U: Update
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
    // CRUD : R: Read
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
        User user =  userRepository.findByEmail(email);
        if(user==null)
            throw new ResourceNotFoundException("User Not Found with e-mail: "+email);
        else return user ;
    }
    // CRUD : D: Delete
    public String deleteUser(int id)throws ResourceNotFoundException{
        if(userRepository.findById(id).orElse(null)==null)
            throw new ResourceNotFoundException("User Not Fount with id: "+id);
        else{
            userRepository.deleteById(id);
            return "User with id: "+id+" has been Deleted !!" ;
        }
    }
}
