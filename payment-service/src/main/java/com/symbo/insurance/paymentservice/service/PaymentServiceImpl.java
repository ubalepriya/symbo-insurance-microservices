package com.symbo.insurance.paymentservice.service;

import com.symbo.insurance.paymentservice.model.Order;
import com.symbo.insurance.paymentservice.proxy.ProxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    private ProxyService proxyService;

    public PaymentServiceImpl(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    @Override
    public boolean processPayment(String orderId) throws Exception {
        boolean isPaymentProcessed = false;
        log.info("Processing payment for order :: "+orderId);

        if(orderId != null){
            Order order = proxyService.getOrderDetails(orderId);
            if (order != null) {
                log.info("Order found.");
                double orderAmount = order.getOrderAmount();
                //Call Payment Gateway to process payment of this amount.
                isPaymentProcessed = true;

                //Update order status.
                proxyService.updatePaymentStatus(orderId, isPaymentProcessed);
            }
        }
        return isPaymentProcessed;
    }
}
