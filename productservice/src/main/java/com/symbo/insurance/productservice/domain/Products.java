package com.symbo.insurance.productservice.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Products {

    @Id
    private String productId;
    private String productName;
    private String productDesc;
    private int productRate;
    private int stockLimit;
}
