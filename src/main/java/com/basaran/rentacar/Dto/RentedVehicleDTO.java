package com.basaran.rentacar.Dto;

import java.time.LocalDate;

public class RentedVehicleDTO {

    private Long id;
    private Long userId;
    private Long vehicleId;
    private LocalDate rentDate;
    private LocalDate returnDate;
    private Double totalPrice;
    private String vehicleMake;
    private String vehicleModel;

    public RentedVehicleDTO() {
    }

    public RentedVehicleDTO(Long id, Long userId, Long vehicleId, LocalDate rentDate, LocalDate returnDate, Double totalPrice, String vehicleMake, String vehicleModel) {
        this.id = id;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.totalPrice = totalPrice;
        this.vehicleMake = vehicleMake;
        this.vehicleModel = vehicleModel;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }

    public LocalDate getRentDate() { return rentDate; }
    public void setRentDate(LocalDate rentDate) { this.rentDate = rentDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }

    public String getVehicleMake() { return vehicleMake; }
    public void setVehicleMake(String vehicleMake) { this.vehicleMake = vehicleMake; }

    public String getVehicleModel() { return vehicleModel; }
    public void setVehicleModel(String vehicleModel) { this.vehicleModel = vehicleModel; }
}
