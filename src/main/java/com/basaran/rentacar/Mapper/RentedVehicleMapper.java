package com.basaran.rentacar.Mapper;

import com.basaran.rentacar.Dto.RentedVehicleDTO;
import com.basaran.rentacar.Entity.RentedVehicle;
import org.springframework.stereotype.Component;

@Component
public class RentedVehicleMapper {

    public static RentedVehicleDTO toDTO(RentedVehicle entity) {
        if (entity == null) return null;

        RentedVehicleDTO dto = new RentedVehicleDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setVehicleId(entity.getVehicle().getId());
        dto.setRentDate(entity.getRentDate());
        dto.setReturnDate(entity.getReturnDate());
        dto.setTotalPrice(entity.getTotalPrice());
        dto.setVehicleMake(entity.getVehicle().getMake());
        dto.setVehicleModel(entity.getVehicle().getModel());

        return dto;
    }
}
