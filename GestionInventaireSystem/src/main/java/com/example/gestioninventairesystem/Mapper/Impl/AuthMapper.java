package com.example.gestioninventairesystem.Mapper.Impl;

import com.example.gestioninventairesystem.Dto.AuthDto;
import com.example.gestioninventairesystem.Mapper.InternalMapper;
import com.example.gestioninventairesystem.Model.User;

import java.util.List;
import java.util.stream.Collectors;

public class AuthMapper implements InternalMapper<AuthDto, User> {

    public AuthDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return new AuthDto(user.getUsername(), user.getPassword());
    }

    public User toModel(AuthDto authDto) {
        if (authDto == null) {
            return null;
        }
        User user = new User();
        user.setEmail(authDto.getEmail());
        user.setPassword(authDto.getPassword());
        return user;
    }

    public List<AuthDto> toDtos(List<User> users) {
        if (users == null || users.isEmpty()) {
            return List.of();
        }
        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<User> toModels(List<AuthDto> authDtos) {
        if (authDtos == null || authDtos.isEmpty()) {
            return List.of();
        }
        return authDtos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
