package com.symbo.insurance.cartservice.services;

import com.symbo.insurance.cartservice.domain.Cart;
import com.symbo.insurance.cartservice.model.CartModel;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CartService {
    CartModel addToCart(String userId, String productId) throws Exception;
    List<Cart> viewCart(String userId) throws Exception;
}
