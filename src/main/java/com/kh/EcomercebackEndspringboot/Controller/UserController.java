package com.kh.EcomercebackEndspringboot.Controller;

import com.kh.EcomercebackEndspringboot.Entity.DTO.GlobalResponse;
import com.kh.EcomercebackEndspringboot.Entity.User;
import com.kh.EcomercebackEndspringboot.Exception.ResourceNotFoundException;
import com.kh.EcomercebackEndspringboot.Service_Int.UserServiceInt;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserServiceInt userServiceInt;
    public UserController(UserServiceInt userServiceInt){
        this.userServiceInt=userServiceInt ;
    }
    @PostMapping("/addUser")
    public ResponseEntity<GlobalResponse> createNewUser(@RequestBody @Valid User user){
        String result = userServiceInt.createUser(user);
        return ResponseEntity.ok().body(
                new GlobalResponse("201 Created",result)
        );
    }
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<GlobalResponse> updateUser(@PathVariable @Valid int id ,@RequestBody @Valid User user) throws ResourceNotFoundException {
        User result = userServiceInt.updateUser(id,user);
        return ResponseEntity.ok().body(
                new GlobalResponse("200 Updated",result)
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
