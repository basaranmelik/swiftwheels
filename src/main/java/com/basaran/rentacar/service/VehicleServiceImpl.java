package com.basaran.rentacar.service;

import com.basaran.rentacar.Dto.VehicleDTO;
import com.basaran.rentacar.Entity.Vehicle;
import com.basaran.rentacar.Mapper.VehicleMapper;
import com.basaran.rentacar.Repository.VehicleRepository;
import com.basaran.rentacar.service.VehicleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void addVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle = VehicleMapper.toEntity(vehicleDTO);
        vehicleRepository.save(vehicle);
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepository.findAll()
                .stream()
                .map(VehicleMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Override
    public VehicleDTO getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Araç bulunamadı: ID = " + id));
        return VehicleMapper.toDTO(vehicle);
    }
    @Override
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }
    @Override
    public void updateVehicle(Long id, VehicleDTO updatedDto) {
        Vehicle existing = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Araç bulunamadı: " + id));

        // Alanları güncelle
        existing.setVehicle_type(updatedDto.getVehicle_type());
        existing.setMake(updatedDto.getMake());
        existing.setModel(updatedDto.getModel());
        existing.setYear(updatedDto.getYear());
        existing.setKm(updatedDto.getKm());
        existing.setColor(updatedDto.getColor());
        existing.setEnginePower(updatedDto.getEnginePower());
        existing.setEngineCapacity(updatedDto.getEngineCapacity());
        existing.setPrice(updatedDto.getPrice());
        existing.setAvailability(updatedDto.getAvailability());
        existing.setFuel(updatedDto.getFuel());

        if (updatedDto.getImage() != null && updatedDto.getImage().length > 0) {
            existing.setImage(updatedDto.getImage());
        }

        vehicleRepository.save(existing);
    }
    public List<VehicleDTO> searchByMakeOrModel(String keyword) {
        return vehicleRepository.findAll().stream()
                .filter(v -> v.getMake().toLowerCase().contains(keyword.toLowerCase()) ||
                        v.getModel().toLowerCase().contains(keyword.toLowerCase()))
                .map(VehicleMapper::toDTO)
                .collect(Collectors.toList());
    }


}

