package com.symbo.insurance.productservice.services;

import com.symbo.insurance.productservice.domain.Products;

public interface ProductService {
    Products getProduct(String productId) throws Exception;
    boolean isProductInStock(String productId) throws Exception;
    Products reduceStock(String productId) throws Exception;
}
