package com.kh.EcomercebackEndspringboot.Controller;

import com.kh.EcomercebackEndspringboot.Entity.DTO.AuthRequest;
import com.kh.EcomercebackEndspringboot.Entity.DTO.GlobalResponse;
import com.kh.EcomercebackEndspringboot.Entity.User;
import com.kh.EcomercebackEndspringboot.Exception.ResourceNotFoundException;
import com.kh.EcomercebackEndspringboot.Service.JwtService;
import com.kh.EcomercebackEndspringboot.Service_Int.UserServiceInt;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Validated
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserServiceInt userServiceInt;
    private final JwtService jwtService ;
    public UserController(UserServiceInt userServiceInt,JwtService jwtService){
        this.userServiceInt=userServiceInt ;
        this.jwtService=jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<GlobalResponse> loginMethode(@RequestBody @Valid AuthRequest authRequest) throws ResourceNotFoundException {
        User user = userServiceInt.searchUserByEmail(authRequest.getEmail());
        String token = jwtService.generateJwtToken(user.getEmail(),user.getRole());
        HashMap<String,String> res = new HashMap<>();
        res.put("message","Login Successfully");
        res.put("token",token);
        return ResponseEntity.ok().body(
                new GlobalResponse(HttpStatus.OK.toString(),res)
        );
    }
    @PostMapping("/addUser")
    public ResponseEntity createNewUser(@RequestBody @Valid User user){
        String result = userServiceInt.createUser(user);
        String token = jwtService.generateJwtToken(user.getEmail(),user.getRole());
        HashMap<String,String> res = new HashMap<>();
        res.put("message",result);
        res.put("token",token);
        return ResponseEntity.ok().body(
                new GlobalResponse<>(HttpStatus.CREATED.toString(),res)
        );
    }
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<GlobalResponse> updateUser(@PathVariable @Valid int id ,@RequestBody @Valid User user) throws ResourceNotFoundException {
        User result = userServiceInt.updateUser(id,user);
        return ResponseEntity.ok().body(
                new GlobalResponse<>("200 Updated",result)
        );
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
