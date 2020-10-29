package com.symbo.insurance.cartservice.model;

import org.springframework.data.annotation.Id;

public class Address {
    private String id;
    private AddressType addressType;
    private String pinCode;
    private String addLine1;
    private String addLine2;
    private String city;
    private String state;
    private String country;
}
