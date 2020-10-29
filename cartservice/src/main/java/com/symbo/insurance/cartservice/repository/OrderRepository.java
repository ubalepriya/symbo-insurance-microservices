package com.symbo.insurance.cartservice.repository;

import com.symbo.insurance.cartservice.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order,String> {
    Iterable<Order> findAllByUserId(String userId);
}
