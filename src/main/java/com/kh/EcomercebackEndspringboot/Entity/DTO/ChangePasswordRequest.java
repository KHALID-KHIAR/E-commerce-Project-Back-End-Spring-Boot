package com.kh.EcomercebackEndspringboot.Entity.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
    @Email(message = "Provide a valid Email !!!")
    private String email ;
    @Size(min = 3,message = "Password should be greater than 3 !!")
    private String password ;
    @Size(min = 3,message = "Password should be greater than 3 !!")
    private String newPassword ;
}
