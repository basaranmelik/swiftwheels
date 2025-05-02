package com.basaran.rentacar.Mapper;

import com.basaran.rentacar.Dto.UserDTO;
import com.basaran.rentacar.Entity.User;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class UserMapper {

    public static UserDTO toDTO(User user) {

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPassword(user.getPassword());
        dto.setEmail(user.getEmail());
        dto.setBirthDate(user.getBirthDate());
        dto.setPhoneNumber(user.getPhoneNumber());
        return dto;
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setBirthDate(dto.getBirthDate());
        user.setPhoneNumber(dto.getPhoneNumber());
        return user;
    }
}
