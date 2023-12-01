package com.kh.EcomercebackEndspringboot.Repository;

import com.kh.EcomercebackEndspringboot.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    Product findByName(String name);
    List<Product> findAllByPrice(int price);
    List<Product> findAllByQuantity(int quantity);
}
