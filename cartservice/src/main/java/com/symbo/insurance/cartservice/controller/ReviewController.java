package com.symbo.insurance.cartservice.controller;

import com.symbo.insurance.cartservice.domain.Review;
import com.symbo.insurance.cartservice.services.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ReviewController {

    private ReviewService reviewServce;

    public ReviewController(ReviewService reviewServce){
        this.reviewServce = reviewServce;
    }

    @PostMapping("/giveReview")
    public Review giveReview(@RequestBody Review review){
        Review reviewNew = null;
        try{
            reviewNew = reviewServce.giveReview(review);
        }
        catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return reviewNew;
    }
}
