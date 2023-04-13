package com.example.itaxi.dtos.requests;

import com.example.itaxi.data.models.enums.PaymentType;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {
    private String email;
    private PaymentType paymentType;
    private BigInteger amount;
}
