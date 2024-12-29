package com.example.gestioninventairesystem.Controller;

import com.example.gestioninventairesystem.Application;
import com.example.gestioninventairesystem.Model.Product;
import com.example.gestioninventairesystem.Service.ProductService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.List;

public class ReadProductController {

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> idColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, String> categoryColumn;

    @FXML
    private TableColumn<Product, Integer> stockColumn;

    @FXML
    private TableColumn<Product, Double> priceColumn;

    @FXML
    private TableColumn<Product, String> timestampColumn;

    @FXML
    private TableColumn<Product, Void> actionColumn;

    @FXML
    private Button refreshButton;

    @FXML
    private Button nouveauButton;

    @FXML
    private TextField searchTextField;  // New search field

    private final ProductService productService;

    private ObservableList<Product> productList;

    public ReadProductController() {
        this.productService = new ProductService();
        this.productList = FXCollections.observableArrayList(this.productService.getAllProduct());
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));

        actionColumn.setCellFactory(new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(TableColumn<Product, Void> param) {
                return new TableCell<Product, Void>() {
                    private final Button deleteButton = new Button("Delete");
                    private final Button updateButton = new Button("Update");

                    {
                        deleteButton.setOnAction(event -> {
                            Product product = getTableRow().getItem();
                            if (product != null) {
                                handleDelete(product);
                            }
                        });

                        updateButton.setOnAction(event -> {
                            Product product = getTableRow().getItem();
                            if (product != null) {
                                handleUpdate(product);
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbox = new HBox(10, updateButton, deleteButton);
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });

        productTable.setItems(productList);

        refreshButton.setOnAction(this::handleRefresh);
        nouveauButton.setOnAction(this::handleNouveau);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> handleSearch(newValue));
    }

    private void handleDelete(Product product) {
        if (product != null) {
            productService.deleteProduct(product.getId());
            productList.remove(product);
        }
    }

    private void handleUpdate(Product product) {
        Application.redirectToPageUpdate("update-view.fxml", product.getId());
    }

    private void handleRefresh(ActionEvent event) {
        productList.clear();
        productList.addAll(productService.getAllProduct());
    }

    private void handleNouveau(ActionEvent event) {
        Application.redirectToPage("add-view.fxml");
    }

    private void handleSearch(String query) {
        if (query == null || query.isEmpty()) {
            productTable.setItems(productList);
        } else {
            List<Product> filteredList = this.productService.searchProducts(query);
            for (Product product : productList) {
                if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(product);
                }
            }
            productTable.setItems(FXCollections.observableList(filteredList));
        }
    }
}
