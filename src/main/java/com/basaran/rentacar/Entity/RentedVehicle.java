package com.basaran.rentacar.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "rented_vehicles")
public class RentedVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Kullanıcı: birden çok araç kiralayabilir
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Araç: sadece bir kullanıcıya kiralanabilir (aynı anda)
    @OneToOne
    @JoinColumn(name = "vehicle_id", nullable = false, unique = true)
    private Vehicle vehicle;

    @Column(nullable = false)
    private LocalDate rentDate;

    @Column(nullable = false)
    private LocalDate returnDate;

    @Column(nullable = false)
    private Double totalPrice;

    public RentedVehicle() {}

    public RentedVehicle(User user, Vehicle vehicle, LocalDate rentDate, LocalDate returnDate, Double totalPrice) {
        this.user = user;
        this.vehicle = vehicle;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.totalPrice = totalPrice;
    }

    // Getter / Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDate getRentDate() {
        return rentDate;
    }

    public void setRentDate(LocalDate rentDate) {
        this.rentDate = rentDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
