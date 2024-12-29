package com.example.gestioninventairesystem.Controller;

import com.example.gestioninventairesystem.Application;
import com.example.gestioninventairesystem.Dto.ProductDto;
import com.example.gestioninventairesystem.Logger.Logger;
import com.example.gestioninventairesystem.Model.AlertType;
import com.example.gestioninventairesystem.Model.Product;
import com.example.gestioninventairesystem.Service.ProductService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UpdateProductController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField quantityField;

    @FXML
    private Button updateButton;

    @FXML
    private Button backButton;

    @FXML
    private Label messageLabel;

    private int productId;

    private final ProductService productService;

    public UpdateProductController() {
        this.productService = new ProductService();
    }

    public void setProductId(int productId) {
        this.productId = productId;
        loadProductData();
    }

    // Load product data based on the productId
    private void loadProductData() {
        Product product = productService.findById(productId);
        if (product != null) {
            nameField.setText(product.getName());
            categoryField.setText(product.getCategory());
            priceField.setText(String.valueOf(product.getPrice()));
            quantityField.setText(String.valueOf(product.getStock()));
        } else {
            throwMessage("Product not found.", AlertType.ERROR);
        }
    }

    // Action when the 'Update' button is clicked
    @FXML
    private void onUpdateProductButtonClicked() {
        try
        {
            String name = nameField.getText();
            String category = categoryField.getText();
            String priceText = priceField.getText();
            String quantityText = quantityField.getText();
            this.productService.updateProduct(new ProductDto(name, category, Integer.parseInt(quantityText), Double.parseDouble(priceText)), this.productId);
            this.handleBackButton();
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
