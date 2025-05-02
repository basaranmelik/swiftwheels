package com.basaran.rentacar.service;

import com.basaran.rentacar.Dto.UserDTO;
import com.basaran.rentacar.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    void saveUser(User user);
    void updateUserInfo(String email, String firstName, String lastName, String phoneNumber);
    void deleteByEmail(String email);
    List<UserDTO> getAllUsers();
    void deleteUserById(Long id);
    UserDTO getUserById(Long id);
    void updateUser(Long id, UserDTO dto);

}