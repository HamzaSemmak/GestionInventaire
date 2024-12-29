package com.example.gestioninventairesystem.Service;


import com.example.gestioninventairesystem.Dto.AuthDto;
import com.example.gestioninventairesystem.Logger.Logger;
import com.example.gestioninventairesystem.Model.User;
import com.example.gestioninventairesystem.Repository.Impl.AuthRepository;
import com.example.gestioninventairesystem.Utils.HashUtils;
import com.example.gestioninventairesystem.Utils.StringUtils;

public class AuthService {

    private final AuthRepository authRepository;

    public AuthService() {
        this.authRepository = new AuthRepository();
    }

    public User login(AuthDto authDto) throws Exception {
        if (authDto.getEmail().isEmpty() || authDto.getPassword().isEmpty()) {
            throw new Exception("User email and password cannot be empty.");
        }
        if (!StringUtils.isEmail(authDto.getEmail())) {
            throw new Exception("Invalid email format. Please provide a valid email address.");
        }
        if (!authRepository.checkByEmail(authDto.getEmail())) {
            throw new Exception("Invalid email.");
        }
        User u = authRepository.getUserByEmail(authDto.getEmail());
        String hashPassword = HashUtils.Hash(authDto.getPassword());
        if (!hashPassword.equals(u.getPassword())) {
            throw new Exception("Invalid password.");
        }
        Logger.info("USER CONNECTED SUCCESSFULLY");
        return u;
    }

}
