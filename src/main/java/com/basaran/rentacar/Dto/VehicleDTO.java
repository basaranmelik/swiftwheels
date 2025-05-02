package com.basaran.rentacar.Dto;

public class VehicleDTO {
    private Long id;
    private String vehicle_type;
    private String make;
    private String model;
    private Integer year;
    private Integer km;
    private String color;
    private String enginePower;
    private String engineCapacity;
    private Integer price;
    private Boolean availability;
    private byte[] image;
    private String fuel;
    private String imageBase64; // Base64 görsel için ek alan

    public VehicleDTO() {
    }

    public VehicleDTO(Long id, String vehicle_type, String make, String model, Integer year, Integer km,
                      String color, String enginePower, String engineCapacity,
                      Integer price, Boolean availability, byte[] image, String fuel, String imageBase64) {
        this.id = id;
        this.vehicle_type = vehicle_type;
        this.make = make;
        this.model = model;
        this.year = year;
        this.km = km;
        this.color = color;
        this.enginePower = enginePower;
        this.engineCapacity = engineCapacity;
        this.price = price;
        this.availability = availability;
        this.image = image;
        this.fuel = fuel;
        this.imageBase64 = imageBase64;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getKm() {
        return km;
    }

    public void setKm(Integer km) {
        this.km = km;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(String enginePower) {
        this.enginePower = enginePower;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(String engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
