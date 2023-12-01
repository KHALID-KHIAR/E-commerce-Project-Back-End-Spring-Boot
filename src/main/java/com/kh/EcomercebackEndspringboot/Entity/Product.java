package com.kh.EcomercebackEndspringboot.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "name can not be Empty !!!")
    @Size(min = 3,message = "name should be greater than 3 !!!")
    private String name;

    @Column
    @Min(value = 5,message = "price can not be Empty and should be in '$'  !!!")
    private int price ;

    @Column
    @Min(value = 1,message = "quantity can not be Empty !!!")
    private int quantity ;

    @Column
    private String image ;

    @ManyToMany(mappedBy = "products")
    private List<User> users ;
}
