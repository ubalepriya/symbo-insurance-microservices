package com.symbo.insurance.cartservice.controller;

import com.symbo.insurance.cartservice.domain.Cart;
import com.symbo.insurance.cartservice.feignproxy.ProxyService;
import com.symbo.insurance.cartservice.model.CartModel;
import com.symbo.insurance.cartservice.model.Products;
import com.symbo.insurance.cartservice.model.UserModel;
import com.symbo.insurance.cartservice.repository.CartRepository;
import com.symbo.insurance.cartservice.services.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService ) {
        this.cartService = cartService;
    }

    @GetMapping("/addToCart/userId/{userId}/productId/{productId}")
    public CartModel addToCart(@PathVariable String userId, @PathVariable String productId){
        CartModel cartModel = null;
        try{
            cartModel = cartService.addToCart(userId, productId);
        }
        catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return cartModel;
    }

    @GetMapping("/viewCart/userId/{userId}")
    public List<Cart>  viewCart(@PathVariable String userId){
        List<Cart> cartList = null;
        try{
            cartList = cartService.viewCart(userId);
        }
        catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }

        return cartList;
    }
}
