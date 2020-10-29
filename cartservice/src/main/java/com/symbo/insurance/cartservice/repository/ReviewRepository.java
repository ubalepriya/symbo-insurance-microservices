package com.symbo.insurance.cartservice.repository;

import com.symbo.insurance.cartservice.domain.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, String> {
}
