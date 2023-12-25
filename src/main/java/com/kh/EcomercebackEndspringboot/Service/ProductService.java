package com.kh.EcomercebackEndspringboot.Service;

import com.kh.EcomercebackEndspringboot.Entity.Product;
import com.kh.EcomercebackEndspringboot.Exception.ResourceNotFoundException;
import com.kh.EcomercebackEndspringboot.Repository.ProductRepository;
import com.kh.EcomercebackEndspringboot.Service_Int.ProductServiceInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements ProductServiceInt {
    private final ProductRepository productRepository;
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public  ProductService(ProductRepository pr){
        this.productRepository=pr ;
    }
    //CRUD C : Create
    public String createProduct(Product product){
        productRepository.save(product);
        return "Product Created Successfully" ;
    }
    //CRUD R : Read
    public Product searchById(int id)throws ResourceNotFoundException{
        Product prd =  productRepository.findById(id).orElse(null);
        if(prd==null)
            throw new ResourceNotFoundException("Product Not Found with id: "+id);
        else
            return prd ;

    }
    public List<Product> searchAllProduct(){
        logger.info("All Products Have been listed !!!");
        return productRepository.findAll();}
    public Product searchByName(String name)throws ResourceNotFoundException{
        Product prd = productRepository.findByName(name) ;
        System.err.println(prd);
            if(prd==null)
            throw new ResourceNotFoundException("Product Not Found with name: "+name);
        else return prd;}
    public List<Product> searchByPrice(int price){
        logger.error("GetProduct By Price Has Been done");
        List<Product> products = productRepository.findAllByPrice(price);
         return products ;
    }
    public List<Product> searchByQuantity(int quantity){
        return productRepository.findAllByQuantity(quantity);}
    //CRUD U : Update
    public Product updateProduct(Product product)throws ResourceNotFoundException{
        Product existingProduct = productRepository.findById(product.getId()).orElse(null);
        if(existingProduct==null)
            throw new ResourceNotFoundException("Product Not Found with id: "+product.getId()) ;
            else{
            existingProduct.setName(product.getName());
            existingProduct.setQuantity(product.getQuantity());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setImage(product.getImage());
            existingProduct.setUsers(product.getUsers());
            return productRepository.save(existingProduct) ;
        }
    }
    //CRUD D : Delete
    public String deleteProduct(int id) throws ResourceNotFoundException{
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null)
            throw new ResourceNotFoundException("Product Not Found with id: " + id);
        else {
            productRepository.deleteById(id);
            return "Product with id=:" + id + " has been Deleted !! ";
        }
    }
}
