package com.example.itaxi.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookTripResponse {
    private String message;
    private LocalDateTime dateOfRide = LocalDateTime.now();
    private String name;
    private String phoneNumber;
    private String model;
    private String vehicleNumber;
    private String color;
}
