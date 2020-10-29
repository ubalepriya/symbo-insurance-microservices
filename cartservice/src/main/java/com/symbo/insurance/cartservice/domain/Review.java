package com.symbo.insurance.cartservice.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import java.util.UUID;

@Data
public class Review {

    @Id
    private String id = UUID.randomUUID().toString();
    private String userId;
    private String productId;
    private String review;
}
