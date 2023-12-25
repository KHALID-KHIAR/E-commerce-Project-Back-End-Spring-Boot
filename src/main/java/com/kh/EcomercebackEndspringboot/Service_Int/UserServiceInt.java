package com.kh.EcomercebackEndspringboot.Service_Int;

import com.kh.EcomercebackEndspringboot.Entity.DTO.AuthRequest;
import com.kh.EcomercebackEndspringboot.Entity.DTO.ChangePasswordRequest;
import com.kh.EcomercebackEndspringboot.Entity.User;
import com.kh.EcomercebackEndspringboot.Exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

public interface UserServiceInt {

     HashMap<String, String> handleLogin(AuthRequest authRequest) throws ResourceNotFoundException;
     HashMap<String,Object> handleSignUp(User user) throws Exception;
     ResponseEntity handleChangeUserPassword(ChangePasswordRequest changePasswordRequest) throws ResourceNotFoundException;
     boolean verifyLoginUser(AuthRequest authRequest) throws ResourceNotFoundException;
      boolean verifyEmailVerificationCode(String email ,String code_req);
     void activateUserAccount(String email) throws ResourceNotFoundException;
     User createUser(User user);
     User updateUser(int id,User user) throws ResourceNotFoundException;
     List<User> searchAllUsers();
     User searchUserById(int id)throws ResourceNotFoundException;
     User searchUserByEmail(String email)throws ResourceNotFoundException;
     String deleteUser(int id)throws ResourceNotFoundException;
}
