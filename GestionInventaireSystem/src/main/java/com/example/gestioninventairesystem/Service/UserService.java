package com.example.gestioninventairesystem.Service;

import com.example.gestioninventairesystem.Dto.UserDto;
import com.example.gestioninventairesystem.Logger.Logger;
import com.example.gestioninventairesystem.Mapper.Impl.UserMapper;
import com.example.gestioninventairesystem.Model.User;
import com.example.gestioninventairesystem.Repository.Impl.AuthRepository;
import com.example.gestioninventairesystem.Repository.Impl.UserRepository;
import com.example.gestioninventairesystem.Utils.StringUtils;

import java.util.List;

public class UserService {

    private final UserRepository userRepository;

    private final AuthRepository authRepository;

    private final UserMapper userMapper;

    public UserService() {
        this.userRepository = new UserRepository();
        this.userMapper = new UserMapper();
        this.authRepository = new AuthRepository();
    }

    public List<User> getAllUsers() {
        return userRepository.all();
    }

    public List<User> searchUsers(String value) {
        return userRepository.search(value);
    }

    public User addUser(UserDto userDto) {
        if (userDto.getEmail().isEmpty() || userDto.getUsername().isEmpty() || userDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("User name, email or password cannot be empty.");
        }
        if(!StringUtils.isEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Invalid email format. Please provide a valid email address.");
        }
        if(!authRepository.checkByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email already exist");
        }
        if(userDto.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be longer than 8");
        }
        User u = userMapper.toModel(userDto);
        u = userRepository.add(u);
        if (u != null) {
            Logger.info("USER CREATED SUCCESSFULLY: " + u.toString());
            return u;
        } else {
            throw new RuntimeException("Failed to create user.");
        }
    }

    public User updateUser(UserDto userDto, int id) {
        if (userDto.getEmail().isEmpty() || userDto.getUsername().isEmpty() || userDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("User name, email or password cannot be empty.");
        }
        if(!StringUtils.isEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Invalid email format. Please provide a valid email address.");
        }
        if(!authRepository.checkByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email already exist");
        }
        User existingUser = userRepository.findById(id);
        if (existingUser == null) {
            throw new IllegalArgumentException("User with the given ID not found.");
        }
        User newUser = userMapper.toModel(userDto);
        newUser.setId(existingUser.getId());
        newUser.setTimestamp(existingUser.getTimestamp());
        newUser = userRepository.update(newUser);
        if (newUser == null) {
            throw new RuntimeException("Failed to update user.");
        }
        Logger.info("USER UPDATED SUCCESSFULLY: " + newUser.toString());
        return newUser;
    }

    public void deleteUser(int id) {
        User existingUser = userRepository.findById(id);
        if (existingUser == null) {
            throw new IllegalArgumentException("User with the given ID not found.");
        }
        boolean check = userRepository.delete(id);
        if(check) {
            Logger.info("USER DELETED SUCCESSFULLY: " + existingUser.toString());
        }
    }

}
