package com.kh.EcomercebackEndspringboot.Service_Int;

import com.kh.EcomercebackEndspringboot.Entity.Product;
import com.kh.EcomercebackEndspringboot.Exception.ResourceNotFoundException;

import java.util.List;

public interface ProductServiceInt {
    public String createProduct(Product product);
    public Product searchById(int id)throws ResourceNotFoundException;
    public List<Product> searchAllProduct();
    public Product searchByName(String name)throws ResourceNotFoundException;
    public List<Product> searchByPrice(int price);
    public List<Product> searchByQuantity(int quantity);
    public Product updateProduct(Product product) throws ResourceNotFoundException;
    public String deleteProduct(int id)throws ResourceNotFoundException;

}
