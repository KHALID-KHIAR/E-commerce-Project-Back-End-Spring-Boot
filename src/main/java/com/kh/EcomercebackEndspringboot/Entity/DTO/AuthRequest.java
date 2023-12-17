package com.kh.EcomercebackEndspringboot.Entity.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @NotEmpty(message = "E-mail can not be Empty !!")
    @Email(message = "Enter a Valid E-mail address !!")
    private String email ;
    @NotEmpty(message = "Password can not be Empty !!")
    @Size(min = 3,message = "Password should be greater that 3")
    private String password;
}
