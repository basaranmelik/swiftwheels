package com.basaran.rentacar.Controller.admin;

import com.basaran.rentacar.Dto.VehicleDTO;
import com.basaran.rentacar.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/vehicles") // Bu controller /admin/vehicles ile başlayan URL'leri yönetir
public class AdminVehicleController {

    @Autowired
    private VehicleService vehicleService; // Araç işlemleri için servis katmanı

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
        return "admin/vehicle_list";
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
                             @RequestParam("imageFile") MultipartFile imageFile,
                             RedirectAttributes redirectAttributes) {
        try {
            // Görsel dosyası varsa byte olarak DTO'ya set edilir
            if (imageFile != null && !imageFile.isEmpty()) {
                vehicleDTO.setImage(imageFile.getBytes());
            }

            // Araç ekleme işlemi
            vehicleService.addVehicle(vehicleDTO);

            // Başarılı işlem sonrası yönlendirme ve mesaj eklenmesi
            redirectAttributes.addAttribute("add", true);  // Başarı durumu eklenir
            return "redirect:/admin/vehicles/list"; // Başarıyla listeye yönlendirme

        } catch (Exception e) {
            e.printStackTrace(); // Hata ayıklama

            // Hatalı işlem sonrası yönlendirme ve mesaj eklenmesi
            redirectAttributes.addAttribute("error", "add");  // Hata durumu eklenir
            return "redirect:/admin/vehicles/add"; // Hata sonrası tekrar ekleme sayfasına yönlendirme
        }
    }


    // Belirli bir aracı düzenlemek için form gösteren endpoint
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        VehicleDTO vehicleDTO = vehicleService.getVehicleById(id); // ID ile araç alınır
        model.addAttribute("vehicleDTO", vehicleDTO); // Formda görüntülenmek üzere eklenir
        return "admin/vehicle_edit";
    }

    @PostMapping("/edit/{id}")
    public String updateVehicle(@PathVariable Long id,
                                @ModelAttribute VehicleDTO vehicleDTO,
                                @RequestParam("imageFile") MultipartFile imageFile,
                                RedirectAttributes redirectAttributes) {
        try {
            // Yeni görsel yüklendiyse güncelle
            if (imageFile != null && !imageFile.isEmpty()) {
                vehicleDTO.setImage(imageFile.getBytes());
            }

            // Güncelleme işlemi
            vehicleService.updateVehicle(id, vehicleDTO);

            // Başarı durumunda yönlendirme ve mesaj ekleme
            redirectAttributes.addAttribute("updated", true);  // Başarı parametresi eklenir
            return "redirect:/admin/vehicles/list"; // Başarıyla listeye yönlendirme

        } catch (Exception e) {
            e.printStackTrace();

            // Hata durumunda yönlendirme ve mesaj ekleme
            redirectAttributes.addAttribute("error", "updated");  // Hata parametresi eklenir
            return "redirect:/admin/vehicles/update/" + id; // Hatalı yönlendirme
        }
    }


    @PostMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            vehicleService.deleteVehicle(id); // Silme işlemi servis üzerinden yapılır
            redirectAttributes.addAttribute("deleted", true); // Başarı durumu eklenir
            return "redirect:/admin/vehicles/list"; // Başarılı silme sonrası yönlendirme
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "rental"); // Silme hatası durumu eklenir
            return "redirect:/admin/vehicles/list"; // Hatalı yönlendirme
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
