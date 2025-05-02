package com.basaran.rentacar.Repository;

import com.basaran.rentacar.Entity.RentedVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RentedVehicleRepository extends JpaRepository<RentedVehicle, Long> {
    @Query("SELECT rv FROM RentedVehicle rv " +
            "JOIN FETCH rv.user " +
            "JOIN FETCH rv.vehicle")
    List<RentedVehicle> findAllWithUserAndVehicle();
    @Query("SELECT rv FROM RentedVehicle rv JOIN rv.user u JOIN rv.vehicle v WHERE u.email = :email")
    List<RentedVehicle> findByUserEmail(@Param("email") String email);

}
