package com.kh.EcomercebackEndspringboot.Service_Int;

import com.kh.EcomercebackEndspringboot.Entity.User;
import com.kh.EcomercebackEndspringboot.Exception.ResourceNotFoundException;

import java.util.List;

public interface UserServiceInt {

    public String createUser(User user);
    public User updateUser(int id,User user) throws ResourceNotFoundException;
    public List<User> searchAllUsers();
    public User searchUserById(int id)throws ResourceNotFoundException;
    public User searchUserByEmail(String email)throws ResourceNotFoundException;
    public String deleteUser(int id)throws ResourceNotFoundException;
}
