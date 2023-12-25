package com.kh.EcomercebackEndspringboot.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id ;
    @Column
    @NotEmpty(message = "firstname can not be Empty !!!")
    @Size(min = 3 ,message = "firstname must be greater than 3 !!!")
    private String firstname;
    @Column
    @NotEmpty(message = "lastname can not be Empty !!!")
    @Size(min = 3 ,message = "lastname must be greater than 3 !!!")
    private String lastname;
    @Column(name = "email", unique = true,nullable = false)
    @NotEmpty(message = "email can not be Empty !!!")
    @Email(message = "Enter a Valid e-mail !!!")
    private String email;
    @Column
    @NotEmpty(message = "password can not be Empty !!!")
    @Size(min = 3, message = "Password should be greater than 3")
    private String password;
    @Column
    @NotEmpty(message = "Role can not be Empty!!")
    private String role;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "User_Product",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products ;
    @Column
    private boolean isActive=false;

}
