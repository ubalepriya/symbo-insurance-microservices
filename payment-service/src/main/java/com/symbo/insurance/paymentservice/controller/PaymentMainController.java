package com.symbo.insurance.paymentservice.controller;

import com.symbo.insurance.paymentservice.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class PaymentMainController {

    private PaymentService paymentService;

    public PaymentMainController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/processPayment/orderId/{orderId}")
    public boolean processPayment(@PathVariable String orderId)
    {
        boolean isPaymentDone = false;
        try{
            isPaymentDone = paymentService.processPayment(orderId);
        }
        catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }

        return isPaymentDone;
    }

}
