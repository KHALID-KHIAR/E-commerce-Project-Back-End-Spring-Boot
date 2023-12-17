package com.kh.EcomercebackEndspringboot.Config;

import com.kh.EcomercebackEndspringboot.Filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http.csrf((csrf)->csrf.disable())
                .authorizeHttpRequests(auth ->auth.requestMatchers("/product/**","/user/addUser").permitAll())
                .authorizeHttpRequests((auth)-> auth.anyRequest().authenticated())
                .formLogin((form)->form.loginPage("/login").permitAll())
                .authenticationProvider(authenticationProvider())
                .sessionManagement(se->se.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

        ;
        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService(){
       return new ServiceCustomUserDetails() ;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthProv = new DaoAuthenticationProvider();
        daoAuthProv.setPasswordEncoder(passwordEncoder());
        daoAuthProv.setUserDetailsService(userDetailsService());
        return daoAuthProv;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)throws Exception{
        return authConfig.getAuthenticationManager();
    }
}
