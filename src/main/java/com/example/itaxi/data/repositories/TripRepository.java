package com.example.itaxi.data.repositories;

import com.example.itaxi.data.models.Driver;
import com.example.itaxi.data.models.Trip;
import com.example.itaxi.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findTripsByDriver(Driver driver);

    List<Trip> findTripsByUser(User user);
}
