package com.example.itaxi.dtos.requests;

import com.example.itaxi.data.models.enums.DriverStatus;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDriverRequest {
    private String password;
    private String email;
    private String location;
    private DriverStatus driverStatus;
}
