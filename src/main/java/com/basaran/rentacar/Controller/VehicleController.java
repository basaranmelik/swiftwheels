package com.basaran.rentacar.Controller;

import com.basaran.rentacar.Dto.VehicleDTO;
import com.basaran.rentacar.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    // Tüm araçları listeleyen endpoint
    @GetMapping("/list")
    public String listVehicles(@RequestParam(required = false) String search, Model model) {
        List<VehicleDTO> vehicles;

        if (search != null && !search.isEmpty()) {
            // Arama yapılacaksa, arama sonucu araçları getir
            vehicles = vehicleService.searchByMakeOrModel(search);
        } else {
            // Eğer arama yoksa, tüm araçları listele
            vehicles = vehicleService.getAllVehicles();
        }

        model.addAttribute("vehicles", vehicles);
        model.addAttribute("searchTerm", search);  // Arama terimini formda göster
        return "vehicle_list";
    }

    // Belirli bir aracın detaylarını gösteren endpoint
    @GetMapping("/{id}")
    public String getVehicleDetail(@PathVariable Long id, Model model) {
        VehicleDTO vehicle = vehicleService.getVehicleById(id); // ID ile araç alınır
        model.addAttribute("vehicle", vehicle); // Araç bilgisi modele eklenir

        // Görsel Base64 olarak ayrıştırılıp HTML'de gösterilmek üzere eklenir
        String base64Image = vehicle.getImageBase64();
        model.addAttribute("vehicleImage", base64Image);

        return "vehicle_detail";
    }

}
