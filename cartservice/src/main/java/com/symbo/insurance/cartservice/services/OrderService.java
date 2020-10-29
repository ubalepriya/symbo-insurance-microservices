package com.symbo.insurance.cartservice.services;

import com.symbo.insurance.cartservice.domain.Order;
import com.symbo.insurance.cartservice.model.OrderModel;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface OrderService {
    OrderModel checkout(String userId) throws Exception;
    List<OrderModel> getAllOrders(String userId) throws Exception;
    Order getOrderDetails(String orderId) throws Exception;
    Order updatePaymentStatus(String orderId, boolean paymentStatus) throws Exception;
}
