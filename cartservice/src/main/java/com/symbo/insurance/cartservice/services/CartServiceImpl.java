package com.symbo.insurance.cartservice.services;

import com.symbo.insurance.cartservice.domain.Cart;
import com.symbo.insurance.cartservice.feignproxy.ProxyService;
import com.symbo.insurance.cartservice.model.CartModel;
import com.symbo.insurance.cartservice.model.Products;
import com.symbo.insurance.cartservice.model.UserModel;
import com.symbo.insurance.cartservice.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private ProxyService proxyService;

    public CartServiceImpl(CartRepository cartRepository, ProxyService proxyService) {
        this.cartRepository = cartRepository;
        this.proxyService = proxyService;
    }

    @Override
    public CartModel addToCart(String userId, String productId) throws Exception {
        Cart cart = null;
        boolean userExists = false;
        boolean productExists = false;
        CartModel cartModel = null;
        List<String> errorMessages = new ArrayList<>();

        if(userId != null && productId != null){
            //Check if this user exists.
            UserModel userModel = proxyService.getUserDetails(userId);
            if(userModel != null)
                userExists = true;

            if(userExists){
                //Check if this product exists.
                Products products = proxyService.getProduct(productId);
                if(products != null)
                    productExists = true;

                if(productExists){
                    //If product is in stock, then add it to cart.
                    boolean isProductInStock = false;
                    isProductInStock = proxyService.isProductInStock(productId);
                    if(isProductInStock)
                    {
                        //Check if any active cart exists.
                        log.info("Checking if this product and user exists on an active cart");
                        Optional<Cart> optionalCart = cartRepository.findByUserIdAndProductIdAndStatus(userId, productId, "A");
                        if(optionalCart.isPresent()){
                            cart = optionalCart.get();
                        }
                        else{
                            cart = new Cart();
                            /*
                            double randomNumber = Math.random() * 1000;
                            long idNumber = Math.round(randomNumber);
                            cart.setCartId(String.valueOf(idNumber));*/
                            cart.setProductId(productId);
                            cart.setUserId(userId);
                            cart.setStatus("A");
                        }

                        cart.setQuantity(cart.getQuantity()+ 1);

                        //Save cart and decrease the stock by one
                        cartRepository.save(cart);
                        //decrease stock by one
                        proxyService.reduceStock(productId);

                    }
                    else
                    {
                        errorMessages.add("This product is not in stock right now");
                    }
                }
                else
                {
                    errorMessages.add("No such product");
                }
            }
            else
            {
                errorMessages.add("No such user");
            }
        }
        else{
            errorMessages.add("User ID and Product ID is required");
        }
        cartModel = new CartModel();
        cartModel.setErrorMessages(errorMessages);
        if(cart != null){
            cartModel.setCartId(cart.getCartId());
            cartModel.setProductId(cart.getProductId());
            cartModel.setQuantity(cart.getQuantity());
            cartModel.setUserId(cart.getUserId());
        }

        return cartModel;
    }

    @Override
    public List<Cart> viewCart(String userId) throws Exception {
        List<Cart> cartList = null;

        if(userId != null){
            log.info("Checking if this user exists on an active cart");
            Iterable<Cart> cartIterable  = cartRepository.findAllByUserIdAndStatus(userId, "A");
            if(cartIterable != null){
                log.info("Cart Items Found");
                Iterator cartIterator = cartIterable.iterator();
                cartList = new ArrayList<Cart>();

                while(cartIterator.hasNext()) {
                    Cart cart = (Cart) cartIterator.next();
                    cartList.add(cart);
                }
            }
        }

        return cartList;
    }
}
