package com.basaran.rentacar.Controller.admin;

import com.basaran.rentacar.Dto.VehicleDTO;
import com.basaran.rentacar.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin/vehicles") // Bu controller /admin/vehicles ile başlayan URL'leri yönetir
public class AdminVehicleController {

    @Autowired
    private VehicleService vehicleService; // Araç işlemleri için servis katmanı

    // Tüm araçları listeleyen endpoint
    @GetMapping("/list")
    public String listVehicles(Model model) {
        List<VehicleDTO> vehicles = vehicleService.getAllVehicles(); // Tüm araçlar alınır
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("vehicleDTO", new VehicleDTO());// Model'e eklenir
        return "admin/vehicle_list"; // İlgili HTML template render edilir
    }

    // Araç ekleme formunu gösteren endpoint
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("vehicleDTO", new VehicleDTO()); // Boş DTO form için gönderilir
        return "admin/vehicle_form";
    }

    // Yeni aracı kaydeden endpoint
    @PostMapping("/add")
    public String addVehicle(@ModelAttribute VehicleDTO vehicleDTO,
                             @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            // Görsel dosyası varsa byte olarak DTO'ya set edilir
            if (imageFile != null && !imageFile.isEmpty()) {
                vehicleDTO.setImage(imageFile.getBytes());
            }
            vehicleService.addVehicle(vehicleDTO); // Servis aracılığıyla veritabanına kaydedilir
            return "redirect:/admin/vehicles/list?success"; // Başarılı yönlendirme
        } catch (Exception e) {
            e.printStackTrace(); // Hata ayıklama
            return "redirect:/admin/vehicles/add?error"; // Hatalı yönlendirme
        }
    }

    // Belirli bir aracı düzenlemek için form gösteren endpoint
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        VehicleDTO vehicleDTO = vehicleService.getVehicleById(id); // ID ile araç alınır
        model.addAttribute("vehicleDTO", vehicleDTO); // Formda görüntülenmek üzere eklenir
        return "admin/vehicle_edit";
    }

    // Araç bilgilerini güncelleyen endpoint
    @PostMapping("/edit/{id}")
    public String updateVehicle(@PathVariable Long id,
                                @ModelAttribute VehicleDTO vehicleDTO,
                                @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            // Yeni görsel yüklendiyse güncelle
            if (imageFile != null && !imageFile.isEmpty()) {
                vehicleDTO.setImage(imageFile.getBytes());
            }
            vehicleService.updateVehicle(id, vehicleDTO); // Güncelleme işlemi
            return "redirect:/admin/vehicles/list?updated"; // Başarılı yönlendirme
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/vehicles/update/" + id + "?error"; // Hatalı yönlendirme
        }
    }

    // Aracı silen endpoint
    @PostMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable Long id) {
        try {
            vehicleService.deleteVehicle(id); // Silme işlemi servis üzerinden yapılır
            return "redirect:/admin/vehicles/list?deleted"; // Başarılı silme sonrası yönlendirme
        } catch (Exception e) {
            return "redirect:/admin/vehicles/list?error=general"; // Hata durumunda yönlendirme
        }
    }


    // Belirli bir aracın detaylarını gösteren endpoint
    @GetMapping("/{id}")
    public String getVehicleDetail(@PathVariable Long id, Model model) {
        VehicleDTO vehicle = vehicleService.getVehicleById(id); // ID ile araç alınır
        model.addAttribute("vehicle", vehicle); // Araç bilgisi modele eklenir

        // Görseli Base64 formatında modele eklenir
        String base64Image = vehicle.getImageBase64();
        model.addAttribute("vehicleImage", base64Image);

        return "/admin/vehicle_detail"; // Detay sayfası döndürülür
    }

}
