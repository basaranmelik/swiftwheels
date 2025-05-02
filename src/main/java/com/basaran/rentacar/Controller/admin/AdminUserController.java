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
            userService.deleteUserById(id);
            return "redirect:/admin/users/list?deleted";
        } catch (RuntimeException e) {
            if (e.getMessage().contains("sistem yöneticisidir")) {
                return "redirect:/admin/users/list?error=systemuser";
            }
            return "redirect:/admin/users/list?error=general";
        }
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
    public String updateUser(@PathVariable Long id, @ModelAttribute UserDTO userDTO) {
        userService.updateUser(id, userDTO);
        return "redirect:/admin/users/list?updated";
    }

}
