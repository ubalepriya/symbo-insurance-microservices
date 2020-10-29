package com.symbo.insurance.cartservice.services;

import com.symbo.insurance.cartservice.domain.Review;

public interface ReviewService {
    Review giveReview(Review review) throws Exception;
}
