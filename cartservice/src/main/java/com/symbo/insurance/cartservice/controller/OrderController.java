package com.symbo.insurance.cartservice.controller;

import com.symbo.insurance.cartservice.domain.Order;
import com.symbo.insurance.cartservice.model.OrderModel;
import com.symbo.insurance.cartservice.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/checkout/userId/{userId}")
    public OrderModel checkout(@PathVariable String userId)
    {
        OrderModel orderModel = null;
        try{
            orderModel = orderService.checkout(userId);
        }
        catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return orderModel;
    }


    @GetMapping("/viewOrders/userId/{userId}")
    public List<OrderModel> getAllOrders(@PathVariable String userId){
        List<OrderModel> orderModelList = null;
        try{
            orderModelList = orderService.getAllOrders(userId);
        }
        catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }

        return orderModelList;
    }

    @GetMapping("/getOrderDetails/orderId/{orderId}")
    public Order getOrderDetails(@PathVariable String orderId){
        Order order = null;
        try{
            order   =   orderService.getOrderDetails(orderId);
        }
        catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return order;
    }

    @GetMapping("/getOrderDetails/orderId/{orderId}/paymentStatus/{paymentStatus}")
    public Order updatePaymentStatus(@PathVariable String orderId, @PathVariable boolean paymentStatus){
        Order order = null;
        try{
            order = orderService.updatePaymentStatus(orderId,paymentStatus);
        }
        catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return order;
    }

}
