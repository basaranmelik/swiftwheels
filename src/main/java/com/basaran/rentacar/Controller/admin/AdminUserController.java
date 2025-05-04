package com.basaran.rentacar.Controller.admin;

import com.basaran.rentacar.Dto.UserDTO;
import com.basaran.rentacar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String listUsers(Model model) {
        List<UserDTO> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "/admin/user_list";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            // Kullanıcıyı silme işlemi
            userService.deleteUserById(id); // Silme işlemi
            redirectAttributes.addAttribute("deleted", true); // Başarılı silme durumu
        } catch (Exception e) {
                redirectAttributes.addAttribute("error", "general"); // Sistem kullanıcısı hatası

        }
        return "redirect:/admin/users/list";
    }

    // Kullanıcı düzenleme formunu göster
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        UserDTO user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user_edit";
    }

    // Kullanıcı bilgilerini güncelle
    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute UserDTO userDTO, RedirectAttributes redirectAttributes) {
        try {
            // Kullanıcıyı güncelleme işlemi
            userService.updateUser(id, userDTO);
            redirectAttributes.addAttribute("updated", true); // Başarılı güncelleme durumu
        } catch (Exception e) {

                redirectAttributes.addAttribute("error", "updated"); // Geçersiz email hatası

        }
        return "redirect:/admin/users/list";
    }


}
