package com.symbo.insurance.cartservice.model;

import lombok.Data;

@Data
public class Products {
    private String productId;
    private String productName;
    private String productDesc;
    private int productRate;
    private int stockLimit;
}
