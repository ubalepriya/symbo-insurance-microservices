package com.symbo.insurance.productservice.repository;

import com.symbo.insurance.productservice.domain.Products;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Products, String> {


}
