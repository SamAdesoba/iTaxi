package com.example.itaxi.services;

import com.example.itaxi.data.models.Driver;
import com.example.itaxi.data.models.Trip;
import com.example.itaxi.dtos.requests.*;
import com.example.itaxi.dtos.response.*;
import com.example.itaxi.exceptions.*;

import java.util.List;

public interface DriverService{
    RegisterDriverResponse register(RegisterDriverRequest request) throws MismatchedPasswordException, UserExistException;

    Driver getDriver(String location) throws NoDriverFoundException;
    RegisterVehicleResponse registerVehicle(RegisterVehicleRequest request) throws InvalidDriverException, InvalidActionException;

    LoginDriverResponse login(LoginDriverRequest request) throws InvalidDriverException, IncorrectPasswordException;
    List<Trip> getHistoryOfAllTrips(String email) throws NoTripHistoryForUserException;
    BookingResponse bookingDetails();
    PaymentResponse payment(PaymentRequest request) throws InvalidUserException;


}
