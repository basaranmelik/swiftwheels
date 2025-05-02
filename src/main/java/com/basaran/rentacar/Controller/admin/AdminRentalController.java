package com.basaran.rentacar.Controller.admin;

import com.basaran.rentacar.Entity.RentedVehicle;
import com.basaran.rentacar.service.RentedVehicleServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/rentals")
public class AdminRentalController {

    private final RentedVehicleServiceImpl rentedVehicleServiceImpl;

    public AdminRentalController(RentedVehicleServiceImpl rentedVehicleServiceImpl) {
        this.rentedVehicleServiceImpl = rentedVehicleServiceImpl;
    }

    @GetMapping("/list")
    public String getAllRentals(Model model) {
        List<RentedVehicle> rentals = rentedVehicleServiceImpl.getAllWithUserAndVehicle();
        model.addAttribute("rentals", rentals);
        return "admin/rented_list";
    }

    @PostMapping("/delete/{id}")
    public String deleteRental(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            rentedVehicleServiceImpl.deleteRentalById(id);
            redirectAttributes.addAttribute("deleted", true);
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", true);
        }
        return "redirect:/admin/rentals/list";
    }
}
