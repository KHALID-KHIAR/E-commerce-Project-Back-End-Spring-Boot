package com.kh.EcomercebackEndspringboot.Controller;

import com.kh.EcomercebackEndspringboot.Entity.DTO.GlobalResponse;
import com.kh.EcomercebackEndspringboot.Entity.Product;
import com.kh.EcomercebackEndspringboot.Exception.ResourceNotFoundException;
import com.kh.EcomercebackEndspringboot.Service_Int.ProductServiceInt;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/product")
public class ProductController {

    private final ProductServiceInt productServiceInt ;
    public ProductController(ProductServiceInt productServiceInt){
        this.productServiceInt=productServiceInt;
    }
    @PostMapping("/addProduct")
    public ResponseEntity<GlobalResponse> createProduct(@RequestBody @Valid Product product){
        String result = productServiceInt.createProduct(product);
        return ResponseEntity.ok().body(
                new GlobalResponse(HttpStatus.CREATED.toString(),result)
        );
    }
    @PutMapping("/updateProduct")
    public ResponseEntity<GlobalResponse> updateProduct(@RequestBody @Valid Product product)throws ResourceNotFoundException {
        Product prd = productServiceInt.updateProduct(product);
        return ResponseEntity.ok().body(
                new GlobalResponse(HttpStatus.OK.toString(),prd)
        );
    }
    @GetMapping("/getAllProducts")
    public ResponseEntity<GlobalResponse> getAllProducts()throws Exception{
        return ResponseEntity.ok().body(
                new GlobalResponse<>(HttpStatus.OK.toString(),productServiceInt.searchAllProduct())
        );
    }
    @GetMapping("/getProductById/{id}")
    public ResponseEntity<GlobalResponse> getProductById(@PathVariable @Valid int id)throws ResourceNotFoundException{
        Product prd = productServiceInt.searchById(id);
        return ResponseEntity.ok().body(
                new GlobalResponse<>(HttpStatus.OK.toString(),prd)
        );
    }
    @GetMapping("/getProductByName/{name}")
    public ResponseEntity<GlobalResponse> getProductByName(@PathVariable @Valid @Size(min=3) String name)throws ResourceNotFoundException{
        Product prd = productServiceInt.searchByName(name);
        return ResponseEntity.ok().body(
                new GlobalResponse<>(HttpStatus.OK.toString(),prd)
        );
    }
    @GetMapping("/getProductByPrice/{price}")
    public ResponseEntity<GlobalResponse> getProductByPrice(@PathVariable @Valid @Min(value = 5) int price){
        List<Product> products = productServiceInt.searchByPrice(price);
        return ResponseEntity.ok().body(
                new GlobalResponse<>(HttpStatus.OK.toString(),products)
        );
    }
    @GetMapping("/getProductByQuantity/{quantity}")
    public ResponseEntity<GlobalResponse> getProductByQuantity(@PathVariable @Valid @Min(value = 1) int quantity){
        List<Product> products = productServiceInt.searchByQuantity(quantity);
        return ResponseEntity.ok().body(
                new GlobalResponse<>(HttpStatus.OK.toString(),products)
        );
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<GlobalResponse> deleteProduct(@PathVariable @Valid int id)throws ResourceNotFoundException{
        return ResponseEntity.ok().body(
                new GlobalResponse(HttpStatus.OK.toString(),productServiceInt.deleteProduct(id))
        )    ;
    }
}
