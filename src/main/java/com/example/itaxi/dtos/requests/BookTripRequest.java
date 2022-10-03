package com.example.itaxi.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookTripRequest {
    private String pickUpAddress;
    private String dropOffAddress;
    private String email;
    private String location;


}
