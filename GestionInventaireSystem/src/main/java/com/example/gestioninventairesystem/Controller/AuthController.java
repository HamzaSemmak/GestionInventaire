package com.example.gestioninventairesystem.Controller;

import com.example.gestioninventairesystem.Application;
import com.example.gestioninventairesystem.Dto.AuthDto;
import com.example.gestioninventairesystem.Logger.Logger;
import com.example.gestioninventairesystem.Model.AlertType;
import com.example.gestioninventairesystem.Model.User;
import com.example.gestioninventairesystem.Service.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AuthController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label messageLabel;

    private final AuthService authService;

    public AuthController() {
        this.authService = new AuthService();
    }

    @FXML
    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        try {
            AuthDto authDto = new AuthDto(username, password);
            User user = authService.login(authDto);
            if (user != null) {
                throwMessage("Hello, you are connected as " + user.getEmail(), AlertType.SUCCESS);
                Application.login(user);
            }
        } catch (Exception e) {
            throwMessage(e.getMessage(), AlertType.ERROR);
        }
    }


    private void throwMessage(String message, AlertType alertType) {
        if (alertType == AlertType.SUCCESS) {
            messageLabel.setText(message);
            messageLabel.setStyle("-fx-text-fill: green;");
        } else if(alertType == AlertType.ERROR){
            Logger.error(message);
            messageLabel.setText(message);
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }
}
