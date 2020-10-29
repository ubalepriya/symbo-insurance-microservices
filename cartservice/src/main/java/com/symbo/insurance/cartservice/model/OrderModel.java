package com.symbo.insurance.cartservice.model;

import com.symbo.insurance.cartservice.domain.Cart;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
public class OrderModel {
    private String orderId;
    private List<Cart> cartList;
    private String userId;
    private double orderAmount;
    private boolean isPaymentDone;
    private boolean isOrderShipped;
    private boolean isOrderDelivered;
    private List<String> errorMessages;
}
