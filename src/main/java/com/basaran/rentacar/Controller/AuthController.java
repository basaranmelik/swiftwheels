package com.basaran.rentacar.Controller;

import com.basaran.rentacar.Dto.UserDTO;
import com.basaran.rentacar.Entity.User;
import com.basaran.rentacar.Mapper.UserMapper;
import com.basaran.rentacar.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Controller
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") UserDTO dto, RedirectAttributes redirectAttributes) {
        try {
            User user = UserMapper.toEntity(dto);
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setRoles(Set.of("ROLE_USER"));
            userService.saveUser(user);

            redirectAttributes.addAttribute("registerSuccess", true);
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "registrationError");
            return "redirect:/register";
        }
    }

    @GetMapping("/profile")
    public String showProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                                @RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String phoneNumber,
                                RedirectAttributes redirectAttributes) {
        try {
            String email = userDetails.getUsername();
            userService.updateUserInfo(email, firstName, lastName, phoneNumber);
            redirectAttributes.addAttribute("success", true);
            return "redirect:/profile";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "profileUpdateError");
            return "redirect:/profile";
        }
    }


    @PostMapping("/profile/delete")
    public String deleteAccount(@AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes redirectAttributes) {
        String email = userDetails.getUsername();
        try {
            System.out.println("Silme işlemi başlatıldı: " + email);
            userService.deleteByEmail(email);
            return "redirect:/logout";
        } catch (Exception e) {
            e.printStackTrace(); // Hatanın detayını logla
            return "redirect:/profile";
        }
        }


    @GetMapping("/")
    public String profilePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("email", userDetails.getUsername());
        return "homepage";
    }
}
