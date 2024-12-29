package com.example.gestioninventairesystem.Dto;

public class AuthDto {

    private String email;

    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public AuthDto() {
    }

    public AuthDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
