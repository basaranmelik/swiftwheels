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
    public String register(@ModelAttribute("user") UserDTO dto) {
        User user = UserMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Set.of("ROLE_USER"));
        userService.saveUser(user);
        return "redirect:/login?registerSuccess";
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
                                @RequestParam String phoneNumber) {
        String email = userDetails.getUsername();
        userService.updateUserInfo(email, firstName, lastName, phoneNumber);
        return "redirect:/?success";
    }

    @GetMapping("/profile/delete")
    public String deleteAccount(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        userService.deleteByEmail(email);
        return "redirect:/login?deleted";
    }

    @GetMapping("/")
    public String profilePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("email", userDetails.getUsername());
        return "homepage";
    }
}