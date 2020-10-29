package com.symbo.insurance.cartservice.feignproxy;

import com.symbo.insurance.cartservice.model.Products;
import com.symbo.insurance.cartservice.model.UserModel;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "netflix-zuul-api-gateway")
@RibbonClients({
        @RibbonClient(name = "product-service"),
        @RibbonClient(name = "user-service")
})

public interface ProxyService {

    @GetMapping("/user-service/authenticateUser/userId/{userId}/password/{password}")
    public boolean authenticateUser(@PathVariable String userId, @PathVariable String password);

    @GetMapping("/user-service/getUserDetails/userId/{userId}")
    public UserModel getUserDetails(@PathVariable String userId);

    @GetMapping("/product-service/getProduct/productId/{productId}")
    public Products getProduct(@PathVariable String productId);

    @GetMapping("/product-service/checkProductStock/productId/{productId}")
    public boolean isProductInStock(@PathVariable String productId);

    @GetMapping("/product-service/reduceStock/productId/{productId}")
    public Products reduceStock(@PathVariable String productId);
}
