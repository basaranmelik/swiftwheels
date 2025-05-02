package com.basaran.rentacar.service;

import com.basaran.rentacar.Dto.UserDTO;
import com.basaran.rentacar.Entity.User;
import com.basaran.rentacar.Mapper.UserMapper;
import com.basaran.rentacar.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
    @Override
    public void updateUserInfo(String email, String firstName, String lastName, String phoneNumber) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + email));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        if (user.isSystemUser()) {
            throw new RuntimeException("Bu kullanıcı sistem yöneticisidir ve silinemez.");
        }

        userRepository.deleteById(id);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        return UserMapper.toDTO(user);
    }

    public void updateUser(Long id, UserDTO dto) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setPhoneNumber(dto.getPhoneNumber());


        userRepository.save(existing);
    }
}