package com.kh.EcomercebackEndspringboot.Filter;

import com.kh.EcomercebackEndspringboot.Config.ServiceCustomUserDetails;
import com.kh.EcomercebackEndspringboot.Service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService ;
    @Autowired
    private ServiceCustomUserDetails customUserDetails ;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String token = request.getHeader("Authorization") ;
    if(token!=null){
        if(token.startsWith("Bearer"))
            token = token.split(" ")[1];
    String email ="";
    UserDetails user = null ;
        email=jwtService.extractEmailFromToken(token);
            if(email!=null && jwtService.validateJwtToken(token,email)){
         user = customUserDetails.loadUserByUsername(email);

        UsernamePasswordAuthenticationToken userPassAuthToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        userPassAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(userPassAuthToken);

    }}
    filterChain.doFilter(request,response);

    }
}
