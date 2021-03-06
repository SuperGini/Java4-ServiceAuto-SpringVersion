package com.gini.iordache.dao.iterfaces;

import com.gini.iordache.entity.auto.Vehicle;

import java.util.Optional;

public interface VehicleDao {
    void createVehicle(Vehicle vehicle);

    Optional<Vehicle> findVehicleBySerialNumber(String serialNumber);
}
