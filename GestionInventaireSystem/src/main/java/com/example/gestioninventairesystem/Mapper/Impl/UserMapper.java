package com.example.gestioninventairesystem.Mapper.Impl;


import com.example.gestioninventairesystem.Dto.UserDto;
import com.example.gestioninventairesystem.Mapper.InternalMapper;
import com.example.gestioninventairesystem.Model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper implements InternalMapper<UserDto, User> {

    @Override
    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
    }

    @Override
    public User toModel(UserDto dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    @Override
    public List<UserDto> toDtos(List<User> models) {
        if (models == null || models.isEmpty()) {
            return new ArrayList<>();
        }
        List<UserDto> dtos = new ArrayList<>();
        for (User user : models) {
            dtos.add(toDto(user));
        }
        return dtos;
    }

    @Override
    public List<User> toModels(List<UserDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new ArrayList<>();
        }
        List<User> models = new ArrayList<>();
        for (UserDto dto : dtos) {
            models.add(toModel(dto));
        }
        return models;
    }

}
