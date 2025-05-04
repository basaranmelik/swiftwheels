package com.basaran.rentacar.Controller;

import com.basaran.rentacar.Dto.UserDTO;
import com.basaran.rentacar.Entity.User;
import com.basaran.rentacar.Mapper.UserMapper;
import com.basaran.rentacar.service.UserService;
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
            // DTO'dan entity'ye dönüştürülür
            User user = UserMapper.toEntity(dto);

            // Şifre şifrelenir
            user.setPassword(passwordEncoder.encode(dto.getPassword()));

            // Kullanıcıya rol eklenir
            user.setRoles(Set.of("ROLE_USER"));

            // Kullanıcı veritabanına kaydedilir
            userService.saveUser(user);

            // Başarılı işlem sonrası yönlendirme ve başarı parametresi eklenir
            redirectAttributes.addAttribute("registerSuccess", true);
            return "redirect:/login"; // Başarılı yönlendirme yapılır

        } catch (Exception e) {
            // Hata durumunda yönlendirme ve hata parametresi eklenir
            redirectAttributes.addAttribute("error", "registrationError");
            return "redirect:/register"; // Hata sonrası kayıt sayfasına yönlendirme
        }
    }


    @GetMapping("/profile")
    public String showProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
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

            // Başarılı güncelleme sonrası başarı mesajı eklenir
            redirectAttributes.addAttribute("success", "profileUpdated");
            return "redirect:/profile"; // Profil sayfasına yönlendirilir

        } catch (Exception e) {
            // Hata durumunda yönlendirme yapılır ve hata mesajı eklenir
            redirectAttributes.addAttribute("error", "profileUpdateError");
            return "redirect:/profile"; // Profil sayfasına yönlendirilir
        }
    }


    @GetMapping("/profile/delete")
    public String deleteAccount(@AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes redirectAttributes) {
        try {
            String email = userDetails.getUsername();
            userService.deleteByEmail(email);

            // Başarılı silme işlemi sonrası yönlendirme ve mesaj eklenir
            redirectAttributes.addAttribute("success", "accountDeleted");
            return "redirect:/login"; // Giriş sayfasına yönlendirilir

        } catch (Exception e) {
            // Hata durumunda yönlendirme ve mesaj eklenir
            redirectAttributes.addAttribute("error", "accountDeleteError");
            return "redirect:/profile"; // Profil sayfasına yönlendirilir
        }
    }


    @GetMapping("/")
    public String profilePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("email", userDetails.getUsername());
        return "homepage";
    }
}