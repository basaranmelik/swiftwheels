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
    public String listVehicles(Model model) {
        List<VehicleDTO> vehicles = vehicleService.getAllVehicles(); // Tüm araçlar alınır
        model.addAttribute("vehicles", vehicles); // HTML template'e gönderilir
        return "vehicle_list"; // templates klasöründe vehicle_list.html kullanılmalı
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

    // Marka veya modele göre araç araması yapan endpoint
    @GetMapping("/search")
    public String searchVehicles(@RequestParam("query") String query, Model model) {
        List<VehicleDTO> filtered = vehicleService.searchByMakeOrModel(query); // Arama yapılır
        model.addAttribute("vehicles", filtered); // Sonuçlar liste sayfasına gönderilir
        return "vehicle_list"; // Arama sonuçları aynı listeleme şablonunda gösterilir
    }
}
