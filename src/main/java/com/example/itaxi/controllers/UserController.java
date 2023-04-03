package com.example.itaxi.controllers;

import com.example.itaxi.data.models.Trip;
import com.example.itaxi.dtos.requests.*;
import com.example.itaxi.dtos.response.*;
import com.example.itaxi.exceptions.*;
import com.example.itaxi.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/iTaxi/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid @NotNull RegisterUserRequest request) throws MismatchedPasswordException, UserExistException {
        RegisterUserResponse userdtos = userService.register(request);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status("Success")
                .message("User created ")
                .data(userdtos)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

    }
    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginUserRequest request) throws InvalidUserException {
        LoginUserResponse response = userService.login(request);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status("Okay")
                .message("Welcome Back")
                .data(response)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/orderRide")
    public ResponseEntity<?> bookARide (@RequestBody @Valid @NotNull BookTripRequest request) throws NoDriverFoundException, UserExistException {
        log.info("Order a ride request ===> {}", request);
        BookTripResponse driverdtos = userService.bookARide(request);
        ApiResponse apiResponse = ApiResponse.builder()
                .status("success")
                .message("A driver as been found")
                .data(driverdtos)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
    @GetMapping("/userTripHistory/{email}")
    public List<Trip> getHistoryOfAllTrips(@PathVariable String email)throws NoTripHistoryForUserException{
        return userService.getHistoryOfAllTrips(email);
    }

    @PostMapping("/payment")
    public PaymentResponse makePayment(@RequestBody PaymentRequest request) throws NoTripHistoryForUserException {
        return userService.makePayment(request);
    }
}
