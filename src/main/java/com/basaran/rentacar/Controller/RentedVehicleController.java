package com.basaran.rentacar.Controller;

import com.basaran.rentacar.Dto.RentedVehicleDTO;
import com.basaran.rentacar.Dto.VehicleDTO;
import com.basaran.rentacar.Entity.User;
import com.basaran.rentacar.service.RentedVehicleService;
import com.basaran.rentacar.service.UserService;
import com.basaran.rentacar.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/rentals")
public class RentedVehicleController {

    @Autowired
    private RentedVehicleService rentedService;

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleService vehicleService;

    // Kiralama formunu göster
    @GetMapping("/form/{vehicleId}")
    public String showRentForm(@PathVariable Long vehicleId, Model model) {
        VehicleDTO vehicle = vehicleService.getVehicleById(vehicleId);
        model.addAttribute("vehicleId", vehicleId);
        model.addAttribute("vehicle", vehicle);
        return "rented_form";
    }

    // Kiralama işlemini kaydet
    @PostMapping("/add")
    public String addRental(@ModelAttribute RentedVehicleDTO dto,
                            @AuthenticationPrincipal UserDetails userDetails,
                            RedirectAttributes redirectAttributes) {

        try {
            User user = userService.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

            dto.setUserId(user.getId());

            rentedService.rentVehicle(dto);

            // Başarılı işlem sonrası yönlendirme ve başarı parametresi eklenir
            redirectAttributes.addAttribute("success", "rentalAdded");
            return "redirect:/rentals/list"; // Kiralama listesine yönlendirilir

        } catch (Exception e) {
            // Hata durumu parametresi eklenir
            redirectAttributes.addAttribute("error", "rentalError");
            return "redirect:/rentals/list"; // Kiralama listesine yönlendirilir
        }
    }


    @GetMapping("/list")
    public String listUserRentals(Model model, Principal principal) {
        String email = principal.getName(); // Spring Security ile giriş yapan kullanıcının email’i
        List<RentedVehicleDTO> userRentals = rentedService.getRentalsByUserEmail(email);
        model.addAttribute("rentals", userRentals);
        return "rented_list"; // kullanıcıya özel kiralama listesi sayfası
    }


    @PostMapping("/delete/{id}")
    public String deleteRental(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            rentedService.deleteRentalById(id);

            // Başarılı silme işlemi sonrası yönlendirme ve başarı parametresi eklenir
            redirectAttributes.addAttribute("success", "rentalDeleted");
            return "redirect:/rentals/list"; // Kiralama listesine yönlendirilir

        } catch (Exception e) {
            // Hata durumu parametresi eklenir
            redirectAttributes.addAttribute("error", "rentalDeleteError");
            return "redirect:/rentals/list"; // Kiralama listesine yönlendirilir
        }
    }

}
