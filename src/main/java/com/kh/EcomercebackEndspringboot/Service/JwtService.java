package com.kh.EcomercebackEndspringboot.Service;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService{

    final String secretKey= "MySec#_%re_tK3$E_312y*&%^_)it_h@oMySec#_%re_tK3$E_312y*&%^_)it_h@o?>,kh_90!2" ;
    public String generateJwtToken(String email,String role){
        String token = "";
         token =  Jwts.builder()
                .setHeaderParam("typ",Header.JWT_TYPE)
                .signWith(generateKey(),SignatureAlgorithm.HS512)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()  + 60 * 1000 * 5))
                .claim("Role",role)
                .setSubject(email).compact()
         ;
        System.err.println("Token : "+token);
        return token ;
    }
    public Key generateKey(){
        byte [] keyBytes = secretKey.getBytes();
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());

    }

    public boolean validateTokenExpiration(Date ExpDate){
        return !ExpDate.after(new Date(System.currentTimeMillis())) ;
    }
    public boolean validateJwtToken(String token,String email)  {
        JwtParser jwtParser = Jwts.parser();
        jwtParser.setSigningKey(generateKey());
        Jwt parser = jwtParser.parse(token) ;
        Claims claims = (Claims) parser.getBody();
        if(validateTokenExpiration(claims.getExpiration())){
            return false ;
        }
        if(! claims.getSubject().equals(email)){
            System.out.println("Token not belong to this email"+email);
            return false ;
        }
    return true ;
    }
    public String extractEmailFromToken(String token){
        JwtParser jwtParser = Jwts.parser();
        jwtParser.setSigningKey(generateKey()) ;
        Jwt parsedToken = jwtParser.parse(token);
        Claims claims = (Claims) parsedToken.getBody();
        return claims.getSubject();

    }
}
