package com.kh.EcomercebackEndspringboot.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id ;

    @NotEmpty
    @Email
    @Column(unique = true,nullable = false)
    private String email ;
    private String verification_code;

}
