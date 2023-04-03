package com.example.itaxi.services;

import com.example.itaxi.data.models.*;
import com.example.itaxi.data.repositories.PaymentRepository;
import com.example.itaxi.data.repositories.TripRepository;
import com.example.itaxi.data.repositories.UserRepository;
import com.example.itaxi.data.repositories.VehicleRepository;
import com.example.itaxi.dtos.requests.*;
import com.example.itaxi.dtos.response.*;
import com.example.itaxi.exceptions.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    private final TripRepository tripRepository;
    private final PaymentRepository paymentRepository;
    @Autowired
    private DriverService driverService;

    @Override
    public RegisterUserResponse register(RegisterUserRequest request) throws MismatchedPasswordException, UserExistException {
        if (userRepository.existsByEmail(request.getEmail()))throw  new UserExistException("User Already Exist", HttpStatus.FORBIDDEN);
        User user = User
                .builder()
                .name(request.getName())
                .address(request.getAddress())
                .email(request.getEmail())
                .gender(request.getGender())
                .phoneNumber(request.getPhoneNumber())
                .password(request.getPassword())
                .confirmPassword(request.getConfirmPassword())
                .build();
        if (request.getPassword().equals(request.getConfirmPassword())){
            User savedUser = userRepository.save(user);
            return RegisterUserResponse
                    .builder()
                    .message( "Hello " + savedUser.getName() + " , Your registration was successful")
                    .build();
        }
        throw new MismatchedPasswordException("Password does not match!!!", HttpStatus.FORBIDDEN);
    }

    @Override
    public LoginUserResponse login(LoginUserRequest request) throws InvalidUserException {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()){
            if (user.get().getPassword().equals(request.getPassword())){
                return LoginUserResponse
                        .builder()
                        .message("Welcome back " + user.get().getName() + ". Where will you like to go today?")
                        .build();
            }
            throw new InvalidUserException("Invalid login details!!!", HttpStatus.NOT_ACCEPTABLE);
        }
        throw new InvalidUserException("Invalid login details!!!", HttpStatus.NOT_FOUND);
    }

    @Override
    public BookTripResponse bookARide(BookTripRequest request) throws NoDriverFoundException, UserExistException {
        Optional<User> savedUser = userRepository.findByEmail(request.getEmail());
        if (savedUser.isPresent()){
            Driver assignedDriver = driverService.getDriver(request.getLocation());
            Trip trip = Trip
                    .builder()
                    .dropOffAddress(request.getDropOffAddress())
                    .driver(assignedDriver)
                    .time(LocalDateTime.now())
                    .pickUpAddress(request.getPickUpAddress())
                    .user(savedUser.get())
                    .location(request.getLocation())
                    .build();
            Trip saved = tripRepository.save(trip);
            return getBookTripResponse(assignedDriver, saved);
        }
        throw new UserExistException("User does not exist", HttpStatus.NOT_FOUND);
    }

    @Override
    public List<Trip> getHistoryOfAllTrips(String email) throws NoTripHistoryForUserException {
        Optional<User> savedUser = userRepository.findByEmail(email);
        if (savedUser.isPresent()){
            List<Trip> userTripHistory = tripRepository.findTripsByUser(savedUser.get());
            if (!userTripHistory.isEmpty()){
                return userTripHistory;
            }
        }
        throw new NoTripHistoryForUserException("You have no trip history", HttpStatus.NOT_FOUND);
    }

    private BookTripResponse getBookTripResponse(Driver assignedDriver, Trip saved) throws UserExistException {
        Optional<Vehicle> vehicle = vehicleRepository.findVehicleByDriver(assignedDriver);
        if(vehicle.isPresent()){

            return BookTripResponse.builder()
                    .message("Your trip has been booked successfully")
                    .name(saved.getDriver().getName())
                    .phoneNumber(saved.getDriver().getPhoneNumber())
                    .model(vehicle.get().getCarModel())
                    .color(vehicle.get().getCarColour())
                    .vehicleNumber(vehicle.get().getCarNumber())
                    .dateOfRide(saved.getTime())
                    .build();
        }
        throw new UserExistException("Vehicle is faulty", HttpStatus.NOT_FOUND);
    }
    @Override
    public PaymentResponse makePayment(PaymentRequest paymentRequest) throws NoTripHistoryForUserException {
        List<Trip> trips = getHistoryOfAllTrips(paymentRequest.getEmail());
        if (!trips.isEmpty()){
            Trip trip = trips.get(trips.size() - 1);
            Payment payment = Payment
                    .builder()
                    .paymentType(paymentRequest.getPaymentType())
                    .user(trip.getUser())
                    .amount(paymentRequest.getAmount())
                    .trip(trip)
                    .build();
            Payment savedPayment = paymentRepository.save(payment);
            return PaymentResponse
                    .builder()
                    .message("Your payment of ₦" + savedPayment.getAmount() + " for the trip from " +
                            trip.getPickUpAddress() + " to " + trip.getDropOffAddress() + " was successful!")
                    .build();
        }
        return null;
    }

    @Override
    public UserResponse feedback(String message) {
        return null;
    }

}
