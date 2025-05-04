package com.basaran.rentacar.service;

import com.basaran.rentacar.Dto.RentedVehicleDTO;
import com.basaran.rentacar.Entity.RentedVehicle;

import java.util.List;

public interface RentedVehicleService {
    void rentVehicle(RentedVehicleDTO dto);
    List<RentedVehicleDTO> getRentalsByUserEmail(String email);
    void deleteRentalById(Long id);
    List<RentedVehicle> getAllWithUserAndVehicle();
}
