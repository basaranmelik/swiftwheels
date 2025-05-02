package com.basaran.rentacar.service;

import com.basaran.rentacar.Dto.RentedVehicleDTO;
import com.basaran.rentacar.Entity.RentedVehicle;
import com.basaran.rentacar.Entity.User;
import com.basaran.rentacar.Entity.Vehicle;
import com.basaran.rentacar.Mapper.RentedVehicleMapper;
import com.basaran.rentacar.Repository.RentedVehicleRepository;
import com.basaran.rentacar.Repository.UserRepository;
import com.basaran.rentacar.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentedVehicleServiceImpl implements RentedVehicleService {

    @Autowired
    private RentedVehicleRepository rentedRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private VehicleRepository vehicleRepo;

    @Override
    public void rentVehicle(RentedVehicleDTO dto) {
        // Aynı araç zaten kiralanmışsa hata ver
        if (rentedRepo.findById(dto.getVehicleId()).isPresent()) {
            throw new RuntimeException("Bu araç zaten kiralanmış.");
        }

        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        Vehicle vehicle = vehicleRepo.findById(dto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Araç bulunamadı"));

        RentedVehicle rented = new RentedVehicle();
        rented.setUser(user);
        rented.setVehicle(vehicle);
        rented.setRentDate(dto.getRentDate());
        rented.setReturnDate(dto.getReturnDate());
        rented.setTotalPrice(dto.getTotalPrice());
        vehicle.setAvailability(false);
        rentedRepo.save(rented);
    }

    @Override
    public List<RentedVehicle> getAllWithUserAndVehicle() {
        return rentedRepo.findAllWithUserAndVehicle();
    }
    @Override
    public void deleteRentalById(Long id) {
        Optional<RentedVehicle> optionalRented = rentedRepo.findById(id);
        if (optionalRented.isPresent()) {
            Vehicle vehicle = optionalRented.get().getVehicle();
            vehicle.setAvailability(true); // tekrar kiralanabilir yap
            vehicleRepo.save(vehicle);    // değişikliği kaydet
            rentedRepo.deleteById(id);    // kiralama kaydını sil
        }
    }
    @Override
    public List<RentedVehicleDTO> getRentalsByUserEmail(String email) {
        List<RentedVehicle> rentals = rentedRepo.findByUserEmail(email);
        return rentals.stream().map(RentedVehicleMapper::toDTO).collect(Collectors.toList());
    }

}
