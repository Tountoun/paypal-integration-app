package com.gofar.paypal_integration;

import lombok.Data;

@Data
public class PaymentDto {

    private String method;
    private Double amount;
    private String currency;
    private String description;
}
