package com.symbo.insurance.cartservice.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Data
@Document
public class Cart {
    @Id
    private String cartId = UUID.randomUUID().toString();
    private String userId;
    private String productId;
    private int quantity;
    private String status;
}
