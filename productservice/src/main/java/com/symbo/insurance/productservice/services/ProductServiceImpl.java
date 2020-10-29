package com.symbo.insurance.productservice.services;

import com.symbo.insurance.productservice.domain.Products;
import com.symbo.insurance.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Products getProduct(String productId) throws Exception {
        Products products = null;
        if(productId != null){
            Optional<Products> optionalProducts = productRepository.findById(productId);
            if(optionalProducts.isPresent()){
                products = optionalProducts.get();
            }
        }
        return  products;
    }

    @Override
    public boolean isProductInStock(String productId) throws Exception {
        Products products = null;
        boolean isProductInStock    =   false;
        if(productId != null){
            Optional<Products> optionalProducts = productRepository.findById(productId);
            if(optionalProducts.isPresent()){
                products = optionalProducts.get();
                if(products.getStockLimit() > 0){
                    isProductInStock = true;
                }
            }
        }
        return isProductInStock;
    }

    @Override
    public Products reduceStock(String productId) throws Exception {
        Products products = null;
        if(productId != null){
            Optional<Products> optionalProducts = productRepository.findById(productId);
            if(optionalProducts.isPresent()){
                products = optionalProducts.get();
                int stockLimit = products.getStockLimit();
                if(stockLimit > 0)
                {
                    products.setStockLimit(stockLimit - 1);
                    productRepository.save(products);
                }
            }
        }
        return products;
    }
}
