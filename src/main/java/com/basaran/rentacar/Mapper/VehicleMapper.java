package com.basaran.rentacar.Mapper;

import com.basaran.rentacar.Dto.VehicleDTO;
import com.basaran.rentacar.Entity.Vehicle;

import java.util.Base64;

public class VehicleMapper {

    public static VehicleDTO toDTO(Vehicle vehicle) {
        VehicleDTO dto = new VehicleDTO();
        dto.setId(vehicle.getId());
        dto.setVehicle_type(vehicle.getVehicle_type());
        dto.setMake(vehicle.getMake());
        dto.setModel(vehicle.getModel());
        dto.setYear(vehicle.getYear());
        dto.setKm(vehicle.getKm());
        dto.setColor(vehicle.getColor());
        dto.setEnginePower(vehicle.getEnginePower());
        dto.setEngineCapacity(vehicle.getEngineCapacity());
        dto.setPrice(vehicle.getPrice());
        dto.setAvailability(vehicle.getAvailability());
        dto.setImage(vehicle.getImage());
        dto.setFuel(vehicle.getFuel());

        // Görsel varsa Base64 string olarak da ata
        if (vehicle.getImage() != null) {
            String base64Image = Base64.getEncoder().encodeToString(vehicle.getImage());
            dto.setImageBase64(base64Image);
        }

        return dto;
    }

    public static Vehicle toEntity(VehicleDTO dto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(dto.getId());
        vehicle.setVehicle_type(dto.getVehicle_type());
        vehicle.setMake(dto.getMake());
        vehicle.setModel(dto.getModel());
        vehicle.setYear(dto.getYear());
        vehicle.setKm(dto.getKm());
        vehicle.setColor(dto.getColor());
        vehicle.setEnginePower(dto.getEnginePower());
        vehicle.setEngineCapacity(dto.getEngineCapacity());
        vehicle.setPrice(dto.getPrice());
        vehicle.setAvailability(dto.getAvailability());
        vehicle.setImage(dto.getImage()); // Sadece byte[] veriyi kullan
        vehicle.setFuel(dto.getFuel());

        return vehicle;
    }
}
