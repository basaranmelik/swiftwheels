package com.basaran.rentacar.config;

import com.basaran.rentacar.Entity.User;
import com.basaran.rentacar.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Optional<User> existingAdmin = userRepository.findByEmail("admin@rentacar.com");
        if (existingAdmin.isEmpty()) {
            User admin = new User();
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setEmail("admin@rentacar.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setBirthDate(LocalDate.of(1990, 1, 1));
            admin.setPhoneNumber("0000000000");
            admin.setRoles(Set.of("ROLE_ADMIN"));
            admin.setSystemUser(true); // Silinemez admin
            userRepository.save(admin);
        }
    }
}
