package com.kh.EcomercebackEndspringboot.Config;

import com.kh.EcomercebackEndspringboot.Entity.User;
import com.kh.EcomercebackEndspringboot.Exception.ResourceNotFoundException;
import com.kh.EcomercebackEndspringboot.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ServiceCustomUserDetails implements UserDetailsService {
    @Autowired
    private UserRepository userRepository ;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElse(null) ;
        if (user==null) {
            try{
            throw new ResourceNotFoundException("User Not Found !!!");
            }catch (ResourceNotFoundException e){}
        }
        return new CustomUserDetails(user);
    }
}
