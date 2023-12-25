package com.kh.EcomercebackEndspringboot.Config;

import com.kh.EcomercebackEndspringboot.Entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private String email ;
    private String password ;
    private List roles;
    private boolean isActive;

    public CustomUserDetails(){}
    public CustomUserDetails(User user){
        this.email=user.getEmail();
        this.password=user.getPassword();
        this.roles= Arrays.stream(user.getRole().split(","))
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList()) ;
        this.isActive=user.isActive();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles ;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
