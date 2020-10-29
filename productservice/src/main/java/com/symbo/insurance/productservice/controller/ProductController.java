package com.symbo.insurance.productservice.controller;

import com.symbo.insurance.productservice.domain.Products;
import com.symbo.insurance.productservice.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class ProductController {

    private ProductService productService;
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getProduct/productId/{productId}")
    public Products getProduct(@PathVariable String productId){
        Products products = null;
        if(productId != null){
            try
            {
                products = productService.getProduct(productId);
            }
            catch (Exception ex){
                log.error(ex.getMessage(),ex);
            }

        }
        return  products;
    }

    @GetMapping("/checkProductStock/productId/{productId}")
    public boolean isProductInStock(@PathVariable String productId){

        boolean isProductInStock    =   false;
        if(productId != null){
            try
            {
                isProductInStock = productService.isProductInStock(productId);
            }
            catch (Exception ex){
                log.error(ex.getMessage(),ex);
            }
        }
        return  isProductInStock;
    }

    @GetMapping("/reduceStock/productId/{productId}")
    public Products reduceStock(@PathVariable String productId) {
        Products products = null;
        if(productId != null){
            try
            {
                products = productService.reduceStock(productId);
            }
            catch (Exception ex){
                log.error(ex.getMessage(),ex);
            }

        }
        return products;
    }

}
