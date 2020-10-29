package com.symbo.insurance.cartservice.services;

import com.symbo.insurance.cartservice.domain.Cart;
import com.symbo.insurance.cartservice.domain.Order;
import com.symbo.insurance.cartservice.feignproxy.ProxyService;
import com.symbo.insurance.cartservice.model.CartModel;
import com.symbo.insurance.cartservice.model.OrderModel;
import com.symbo.insurance.cartservice.model.Products;
import com.symbo.insurance.cartservice.model.UserModel;
import com.symbo.insurance.cartservice.repository.CartRepository;
import com.symbo.insurance.cartservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private CartRepository cartRepository;
    private OrderRepository orderRepository;
    private ProxyService proxyService;

    public OrderServiceImpl(CartRepository cartRepository, OrderRepository orderRepository, ProxyService proxyService) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.proxyService = proxyService;
    }

    @Override
    public OrderModel checkout(String userId) throws Exception {
        Order order = null;
        List<Cart> cartList = null;
        double orderAmount = 0;
        boolean userExists = false;
        OrderModel orderModel = null;
        List<String> errorMessages = new ArrayList<>();

        //Check if this user exists
        UserModel userModel = proxyService.getUserDetails(userId);
        if(userModel != null)
            userExists = true;

        if(userExists) {

            //Find all entries in Cart table with this user and status active.
            log.info("Check if there are any cart items by this user :: " + userId);
            Iterable<Cart> cartIterable = cartRepository.findAllByUserIdAndStatus(userId, "A");

            if (cartIterable != null) {
                Iterator cartIterator = cartIterable.iterator();
                cartList = new ArrayList<Cart>();

                while (cartIterator.hasNext()) {
                    Cart cart = (Cart) cartIterator.next();
                    cartList.add(cart);

                    //Calculate order amount
                    //Get Product Details - Call product service
                    Products products = proxyService.getProduct(cart.getProductId());

                    //Get productRate
                    int productRate = products.getProductRate();

                    //Get product quantity
                    int quantity = cart.getQuantity();
                    orderAmount += productRate * quantity;

                    cart.setStatus("D");
                }
                log.info("Found " + cartList.size() + " items.");

                //Create Order object if there are any cart Items
                if (cartList.size() > 0) {
                    log.info("Creating Order object.");
                    order = new Order();
                    /*
                    double randomNumber = Math.random() * 1000;
                    long idNumber = Math.round(randomNumber);
                    order.setOrderId(String.valueOf(idNumber));*/
                    order.setCartList(cartList);
                    order.setOrderAmount(orderAmount);
                    order.setPaymentDone(false);
                    order.setOrderDelivered(false);
                    order.setOrderShipped(false);
                    order.setUserId(userId);

                    //Save Order
                    log.info("Saving Order.");
                    orderRepository.save(order);

                    //Update Cart status to D
                    log.info("Updating Cart Status to D");
                    for (Cart cart : cartList) {
                        cartRepository.save(cart);
                    }
                }
                else{
                    errorMessages.add("Your cart is empty");
                }
            }
        }
        else{
            errorMessages.add("No such user");
        }
        orderModel = new OrderModel();
        orderModel.setErrorMessages(errorMessages);
        if(order != null){
            orderModel.setOrderId(order.getOrderId());
            orderModel.setCartList(order.getCartList());
            orderModel.setOrderAmount(order.getOrderAmount());
            orderModel.setUserId(order.getUserId());
            orderModel.setPaymentDone(order.isPaymentDone());
            orderModel.setOrderShipped(order.isOrderShipped());
            orderModel.setOrderDelivered(order.isOrderDelivered());
        }

        return orderModel;
    }

    @Override
    public List<OrderModel> getAllOrders(String userId) throws Exception {
        List<OrderModel> orderModelList = null;
        List<String> errorMessages = new ArrayList<>();
        boolean userExists = false;

        if(userId != null){
            //Check if this user exists
            UserModel userModel = proxyService.getUserDetails(userId);
            if(userModel != null)
                userExists = true;

            if(userExists) {
                Iterable<Order> orderIterable = orderRepository.findAllByUserId(userId);
                if (orderIterable != null) {
                    Iterator<Order> orderIterator = orderIterable.iterator();
                    orderModelList = new ArrayList<>();
                    while (orderIterator.hasNext()) {
                        Order order = orderIterator.next();
                        OrderModel orderModel = new OrderModel();
                        orderModel.setOrderId(order.getOrderId());
                        orderModel.setCartList(order.getCartList());
                        orderModel.setOrderAmount(order.getOrderAmount());
                        orderModel.setUserId(order.getUserId());
                        orderModel.setPaymentDone(order.isPaymentDone());
                        orderModel.setOrderShipped(order.isOrderShipped());
                        orderModel.setOrderDelivered(order.isOrderDelivered());
                        orderModelList.add(orderModel);
                    }
                }
            }
            else {
                errorMessages.add("No such user");
            }
        }
        else {
            errorMessages.add("User ID is required");
        }
        return orderModelList;
    }

    @Override
    public Order getOrderDetails(String orderId) throws Exception {
        Order order = null;
        if(orderId != null){
            Optional<Order> orderOptional = orderRepository.findById(orderId);
            if(orderOptional.isPresent())
                order = orderOptional.get();
        }
        return order;
    }

    @Override
    public Order updatePaymentStatus(String orderId, boolean paymentStatus)  throws Exception{
        Order orderNew = null;
        if(orderId != null){
            Optional<Order> orderOptional = orderRepository.findById(orderId);
            if(orderOptional.isPresent())
            {
                Order order = orderOptional.get();
                order.setPaymentDone(paymentStatus);
                orderNew = orderRepository.save(order);
            }
        }
        return orderNew;
    }
}
