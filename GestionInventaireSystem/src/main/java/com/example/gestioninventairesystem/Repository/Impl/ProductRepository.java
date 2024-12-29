package com.example.gestioninventairesystem.Repository.Impl;


import com.example.gestioninventairesystem.Config.Database;
import com.example.gestioninventairesystem.Logger.Logger;
import com.example.gestioninventairesystem.Model.Product;
import com.example.gestioninventairesystem.Repository.JdbcRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductRepository implements JdbcRepository<Product> {

    private static final String TABLE_NAME = "products";
    private final Connection connection;

    public ProductRepository() {
        this.connection = Database.connection();
    }

    public Product add(Product product) {
        String query = "INSERT INTO " + TABLE_NAME + " (name, category, stock, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            setProductParameters(statement, product);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        product.setId(generatedKeys.getInt(1));
                    }
                }
            }
            return product;
        } catch (SQLException e) {
            throw new RuntimeException("Error adding product", e);
        }
    }

    public Product update(Product product) {
        String query = "UPDATE " + TABLE_NAME + " SET name = ?, category = ?, stock = ?, price = ?, timestamp = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setProductParameters(statement, product);
            statement.setString(5, product.getTimestamp());
            statement.setInt(6, product.getId());
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0 ? product : null;
        } catch (SQLException e) {
            Logger.error(e.toString());
            throw new RuntimeException("Error updating product", e);
        }
    }

    public boolean delete(int id) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.error(e.toString());
            throw new RuntimeException("Error deleting product", e);
        }
    }

    public Product findById(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToProduct(resultSet);
                }
                return null;
            }
        } catch (SQLException e) {
            Logger.error(e.toString());
            throw new RuntimeException("Error finding product", e);
        }
    }

    public List<Product> search(String keyword) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE name LIKE ? OR category LIKE ? OR stock LIKE ? OR price LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            String searchValue = "%" + keyword + "%";
            statement.setString(1, searchValue);
            statement.setString(2, searchValue);
            statement.setString(3, searchValue);
            statement.setString(4, searchValue);
            try (ResultSet resultSet = statement.executeQuery()) {
                return mapResultSetToProducts(resultSet);
            }
        } catch (SQLException e) {
            Logger.error(e.toString());
            throw new RuntimeException("Error searching products", e);
        }
    }

    public List<Product> all() {
        String query = "SELECT * FROM " + TABLE_NAME;
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            return mapResultSetToProducts(resultSet);
        } catch (SQLException e) {
            Logger.error(e.toString());
            throw new RuntimeException("Error retrieving all products", e);
        }
    }

    private Product mapResultSetToProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setCategory(resultSet.getString("category"));
        product.setStock(resultSet.getInt("stock"));
        product.setPrice(resultSet.getDouble("price"));
        product.setTimestamp(resultSet.getString("timestamp"));
        return product;
    }

    private List<Product> mapResultSetToProducts(ResultSet resultSet) throws SQLException {
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            products.add(mapResultSetToProduct(resultSet));
        }
        return products.isEmpty() ? Collections.emptyList() : products;
    }

    private void setProductParameters(PreparedStatement statement, Product product) throws SQLException {
        statement.setString(1, product.getName());
        statement.setString(2, product.getCategory());
        statement.setInt(3, product.getStock());
        statement.setDouble(4, product.getPrice());
    }
}
