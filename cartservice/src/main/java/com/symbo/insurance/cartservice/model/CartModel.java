package com.symbo.insurance.cartservice.model;

import lombok.Data;

import java.util.List;

@Data
public class CartModel {

    private String cartId;
    private String userId;
    private String productId;
    private int quantity;
    private List<String> errorMessages;
}
