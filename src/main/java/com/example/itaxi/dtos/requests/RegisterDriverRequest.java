package com.example.itaxi.dtos.requests;

import com.example.itaxi.data.models.enums.Gender;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDriverRequest {
    private String name;
    private String address;
    @Email
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private Gender gender;
    private String password;
    private String confirmPassword;
}
