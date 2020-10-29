package com.symbo.insurance.cartservice.services;

import com.symbo.insurance.cartservice.domain.Review;
import com.symbo.insurance.cartservice.feignproxy.ProxyService;
import com.symbo.insurance.cartservice.model.Products;
import com.symbo.insurance.cartservice.model.UserModel;
import com.symbo.insurance.cartservice.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private ProxyService proxyService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ProxyService proxyService) {
        this.reviewRepository = reviewRepository;
        this.proxyService = proxyService;
    }

    @Override
    public Review giveReview(Review review) throws Exception {
        Review reviewNew = null;
        boolean userExists = false;
        boolean productExists = false;
        boolean productIsPurchasedByUser = true;

        if(review != null){
            log.info("Checking if user is present.");
            //Check if user exists
            UserModel userModel = proxyService.getUserDetails(review.getUserId());
            if(userModel != null)
                userExists = true;

            if(userExists){
                log.info("Checking if product is present.");
                Products products = proxyService.getProduct(review.getProductId());
                if(products != null)
                    productExists = true;

                if(productExists){
                    log.info("Checking if product is purchased by this user.");
                    if(productIsPurchasedByUser){
                        log.info("Saving review");
                        //review.setId(String.valueOf(Math.round(Math.random() * 1000)));
                        reviewRepository.save(review);
                        reviewNew = review;
                        log.info("Review saved.");
                    }
                }
            }
        }


        return reviewNew;
    }
}
