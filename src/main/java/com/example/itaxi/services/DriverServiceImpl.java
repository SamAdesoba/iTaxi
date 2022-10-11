package com.example.itaxi.services;


import com.example.itaxi.data.models.Driver;
import com.example.itaxi.data.models.Trip;
import com.example.itaxi.data.models.Vehicle;
import com.example.itaxi.data.models.enums.DriverStatus;
import com.example.itaxi.data.repositories.DriverRepository;
import com.example.itaxi.data.repositories.TripRepository;
import com.example.itaxi.data.repositories.VehicleRepository;
import com.example.itaxi.dtos.requests.*;
import com.example.itaxi.dtos.response.*;
import com.example.itaxi.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class DriverServiceImpl implements DriverService{

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private TripRepository tripRepository;


    @Override
    public RegisterDriverResponse register(RegisterDriverRequest request) throws MismatchedPasswordException, UserExistException {
        if (driverRepository.existsByEmail(request.getEmail())) throw  new UserExistException("User Already Exist", HttpStatus.FORBIDDEN);
            Driver driver = Driver
                    .builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .gender(request.getGender())
                    .address(request.getAddress())
                    .password(request.getPassword())
                    .confirmPassword(request.getConfirmPassword())
                    .build();
            if(request.getPassword().equals(request.getConfirmPassword())) {
                Driver savedDrive = driverRepository.save(driver);
                return RegisterDriverResponse
                        .builder()
                        .message("Hello " + savedDrive.getName() + " , Your registration was successful")
                        .build();
            }
        throw new MismatchedPasswordException("Password does not match!!!", HttpStatus.FORBIDDEN);
    }

    @Override
    public Driver getDriver(String location) throws NoDriverFoundException {
        List<Driver> drivers = driverRepository.findByLocation(location);
        List<Driver> availableDriver = new ArrayList<>();
        for (Driver driver: drivers) {
            if (driver.getDriverStatus().equals(DriverStatus.AVAILABLE)){
                availableDriver.add(driver);
            }
        }
        if (!availableDriver.isEmpty()){
            SecureRandom random = new SecureRandom();
            return drivers.get(random.nextInt(drivers.size()));
        }
        throw new NoDriverFoundException("No driver available at your location", HttpStatus.NOT_FOUND);
    }

    @Override
    public RegisterVehicleResponse registerVehicle(RegisterVehicleRequest request) throws InvalidDriverException, InvalidActionException {
        Optional<Driver> driver = driverRepository.findByEmail(request.getEmail());
        if (driver.isPresent()){
            Optional<Vehicle> savedVehicle = vehicleRepository.findByDriverId(driver.get().getId());
            if (savedVehicle.isEmpty()){
                Vehicle vehicle = Vehicle
                        .builder()
                        .carNumber(request.getCarNumber())
                        .carModel(request.getCarModel())
                        .carColour(request.getCarColour())
                        .driver(driver.get())
                        .build();
                vehicleRepository.save(vehicle);
                return RegisterVehicleResponse
                        .builder()
                        .message(driver.get().getName() + " your car has been registered successfully. Safe trips")
                        .build();
            }
            throw new InvalidActionException("You can't register more than a car !!!", HttpStatus.FORBIDDEN);
        }
            throw new InvalidDriverException("Invalid Driver details", HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    public LoginDriverResponse login(LoginDriverRequest request) throws InvalidDriverException, IncorrectPasswordException {
        Optional<Driver> driver = driverRepository.findByEmail(request.getEmail());
        if (driver.isPresent()){
            if (driver.get().getPassword().equals(request.getPassword())){
                driver.get().setDriverStatus(request.getDriverStatus());
                driver.get().setLocation(request.getLocation());
                driverRepository.save(driver.get());
                return LoginDriverResponse
                        .builder()
                        .message("Welcome back " + driver.get().getName() + ". Ready to go for some rides ?")
                        .build();
            }
            throw new IncorrectPasswordException("Incorrect Password", HttpStatus.NOT_ACCEPTABLE);
        }
            throw new InvalidDriverException("Invalid Driver details", HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    public List<Trip> getHistoryOfAllTrips(String email) throws NoTripHistoryForUserException {
        Optional<Driver> savedDriver = driverRepository.findByEmail(email);
        if (savedDriver.isPresent()){
            List<Trip> tripHistory = tripRepository.findTripsByDriver(savedDriver.get());
            if (!tripHistory.isEmpty()){
                return tripHistory;
            }
            throw new NoTripHistoryForUserException("You have no trip history", HttpStatus.NOT_FOUND);
        }
        throw new NoTripHistoryForUserException("Invalid login details", HttpStatus.NOT_FOUND);
    }

    @Override
    public BookingResponse bookingDetails() {
        return null;
    }

    @Override
    public PaymentResponse payment(PaymentRequest request) {
        return null;
    }
//
//    @Override
//    public PaymentResponse payment(PaymentRequest request) {
//        Optional<Driver> driver = driverRepository.findByEmail(request.getEmail());
//        if (driver.isPresent()){
//            Payment payment = Payment
//                    .builder()
//                    .paymentType(request.getPaymentType())
//                    .amount(request.getAmount())
//                    .userEmail(request.getUserEmail())
//                    .build();
//            Payment savedPayment = paymentRepository.save(payment);
//
//            PaymentResponse response = new PaymentResponse();
//            response.setMessage(savedPayment);
//
//
//        }
//        throw new InvalidUserException("Invalid Email") ;
//    }
}
