package com.symbo.insurance.cartservice.repository;

import com.symbo.insurance.cartservice.domain.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, String> {
    Optional<Cart> findByUserIdAndProductIdAndStatus(String userId, String productId, String status );
    Iterable<Cart> findAllByUserId(String userId);
    Iterable<Cart> findAllByUserIdAndStatus(String userId, String status);


}
