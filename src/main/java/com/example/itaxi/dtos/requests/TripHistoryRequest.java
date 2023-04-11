package com.example.itaxi.dtos.requests;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripHistoryRequest {
    private String email;
}
