package com.symbo.insurance.paymentservice.model;

import lombok.Data;

@Data
public class Order {
    private String orderId ;
    private double orderAmount;
    private boolean isPaymentDone;
}
