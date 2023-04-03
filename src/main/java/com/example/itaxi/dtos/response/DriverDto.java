package com.example.itaxi.dtos.response;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto{

    private String message;
    private String name;
    private String phoneNumber;
    private String model;
    private String vehicleNumber;
    private String color;

    @Override
    public String toString() {
        return " name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", model='" + model + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", color='" + color + '\'';
    }
}
