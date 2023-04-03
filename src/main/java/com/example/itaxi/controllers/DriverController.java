package com.example.itaxi.controllers;

import com.example.itaxi.data.models.Trip;
import com.example.itaxi.dtos.requests.LoginDriverRequest;
import com.example.itaxi.dtos.requests.RegisterDriverRequest;
import com.example.itaxi.dtos.requests.RegisterVehicleRequest;
import com.example.itaxi.dtos.response.ApiResponse;
import com.example.itaxi.dtos.response.LoginDriverResponse;
import com.example.itaxi.dtos.response.RegisterDriverResponse;
import com.example.itaxi.dtos.response.RegisterVehicleResponse;
import com.example.itaxi.exceptions.*;
import com.example.itaxi.services.DriverService;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/iTaxi/drivers")
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService){
        this.driverService = driverService;
    }
    @PostMapping("/register")
    public ResponseEntity<?>createDriver(@RequestBody @Valid @NotNull RegisterDriverRequest registerDriverRequest) throws MismatchedPasswordException, UserExistException {
        log.info("Account Creation Request ==> {}", registerDriverRequest);
        RegisterDriverResponse driverdtos = driverService.register(registerDriverRequest);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status("success")
                .message("Driver created successfully")
                .data(driverdtos)
                .build();
        log.info("Returning response");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
    @PutMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDriverRequest request) throws InvalidDriverException, IncorrectPasswordException {
        LoginDriverResponse driverdtos = driverService.login(request);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status("Success")
                .message("Welcome Back " + driverdtos)
                .data(driverdtos)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }

    @PostMapping("/registerYourCar")
    public ResponseEntity<?> registerVehicle(@RequestBody RegisterVehicleRequest request) throws InvalidDriverException, InvalidActionException {
        RegisterVehicleResponse driverdtos = driverService.registerVehicle(request);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status("Okay")
                .message("Successful Registration")
                .data(driverdtos)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }

    @GetMapping("/trips/{email}")
    public List<Trip> getHistoryOfAllTrips(@PathVariable String email) throws NoTripHistoryForUserException{
        return driverService.getHistoryOfAllTrips(email);
    }
}
