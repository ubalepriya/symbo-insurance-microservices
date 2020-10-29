package com.symbo.insurance.cartservice.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import java.util.UUID;
import java.util.List;

@Data
public class Order {
    @Id
    private String orderId = UUID.randomUUID().toString();
    @DBRef
    private List<Cart> cartList;
    private String userId;
    private double orderAmount;
    private boolean isPaymentDone;
    private boolean isOrderShipped;
    private boolean isOrderDelivered;

}
