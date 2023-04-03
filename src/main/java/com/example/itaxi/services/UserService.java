package com.example.itaxi.services;

import com.example.itaxi.data.models.Trip;
import com.example.itaxi.dtos.requests.*;
import com.example.itaxi.dtos.response.*;
import com.example.itaxi.exceptions.*;

import java.util.List;

public interface UserService {
    RegisterUserResponse register(RegisterUserRequest request) throws MismatchedPasswordException, UserExistException;
    LoginUserResponse login(LoginUserRequest request) throws InvalidUserException;
    BookTripResponse bookARide(BookTripRequest request) throws NoDriverFoundException, UserExistException;
    List<Trip>getHistoryOfAllTrips(String email) throws NoTripHistoryForUserException;
    PaymentResponse makePayment(PaymentRequest paymentRequest) throws NoTripHistoryForUserException;
    UserResponse feedback(String message);
}
