package com.symbo.insurance.paymentservice.service;

public interface PaymentService {
    boolean processPayment(String orderId) throws Exception;
}
