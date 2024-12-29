package com.example.gestioninventairesystem.Controller;

import com.example.gestioninventairesystem.Application;
import com.example.gestioninventairesystem.Dto.ProductDto;
import com.example.gestioninventairesystem.Logger.Logger;
import com.example.gestioninventairesystem.Model.AlertType;
import com.example.gestioninventairesystem.Service.ProductService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class AddProductController {

    private ProductService productService;

    @FXML
    private TextField nameField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField quantityField;

    @FXML
    private Button addProductButton;

    @FXML
    private Button backButton;

    @FXML
    private Label messageLabel;

    public AddProductController() {
        this.productService = new ProductService();
    }

    @FXML
    public void onAddProductButtonClicked() {
        try
        {
            String name = nameField.getText();
            String category = categoryField.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            this.productService.addProduct(new ProductDto(name, category, quantity, price));
            throwMessage("Product created successfully", AlertType.SUCCESS);
        } catch (Exception e) {
            Logger.error(e.toString());
            throwMessage(e.toString(), AlertType.ERROR);
        }
    }

    @FXML
    public void handleBackButton() {
        Application.redirectToPage("main-view.fxml");
    }

    private void throwMessage(String message, AlertType messageType) {
        messageLabel.setText(message);

        switch (messageType) {
            case SUCCESS:
                messageLabel.setStyle("-fx-text-fill: green;");
                break;
            case ERROR:
                messageLabel.setStyle("-fx-text-fill: red;");
                break;
        }
    }

}
