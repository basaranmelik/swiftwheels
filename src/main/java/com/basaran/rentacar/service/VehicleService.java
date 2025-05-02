package com.basaran.rentacar.service;

import com.basaran.rentacar.Dto.VehicleDTO;
import com.basaran.rentacar.Entity.Vehicle;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VehicleService {
    void addVehicle(VehicleDTO vehicleDTO);
    List<VehicleDTO> getAllVehicles();
    VehicleDTO getVehicleById(Long id);
    void deleteVehicle(Long id);
    void updateVehicle(Long id, VehicleDTO updatedDto);
    List<VehicleDTO> searchByMakeOrModel(String keyword);


}
