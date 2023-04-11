package com.example.itaxi.data.repositories;

import com.example.itaxi.data.models.Driver;
import com.example.itaxi.data.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByDriverId(long id);

    Optional<Vehicle> findVehicleByDriver(Driver driver);
}
