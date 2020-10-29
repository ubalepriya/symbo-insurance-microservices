package com.symbo.insurance.paymentservice.proxy;

import com.symbo.insurance.paymentservice.model.Order;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "netflix-zuul-api-gateway")
@RibbonClient(name = "cart-service")
public interface ProxyService {

    @GetMapping("/cart-service/getOrderDetails/orderId/{orderId}")
    public Order getOrderDetails(@PathVariable String orderId);

    @GetMapping("/cart-service/getOrderDetails/orderId/{orderId}/paymentStatus/{paymentStatus}")
    public Order updatePaymentStatus(@PathVariable String orderId, @PathVariable boolean paymentStatus);

}
