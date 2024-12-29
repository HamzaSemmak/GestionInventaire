package com.example.gestioninventairesystem.Repository.Impl;

import com.example.gestioninventairesystem.Config.Database;
import com.example.gestioninventairesystem.Logger.Logger;
import com.example.gestioninventairesystem.Model.User;
import com.example.gestioninventairesystem.Repository.JdbcRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserRepository implements JdbcRepository<User> {

    private static final String TABLE_NAME = "users";

    private final Connection connection;

    public UserRepository() {
        this.connection = Database.connection();
    }

    public User add(User user) {
        String query = "INSERT INTO " + TABLE_NAME + " (username, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            setUserParameters(statement, user);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                    }
                }
            }
            return user;
        } catch (SQLException e) {
            Logger.error(e.toString());
            throw new RuntimeException("Error adding user", e);
        }
    }

    public User update(User user) {
        String query = "UPDATE " + TABLE_NAME + " SET username = ?, email = ?, password = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setUserParameters(statement, user);
            statement.setInt(4, user.getId());
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0 ? user : null;
        } catch (SQLException e) {
            Logger.error(e.toString());
            throw new RuntimeException("Error updating user", e);
        }
    }

    public boolean delete(int id) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.error(e.toString());
            throw new RuntimeException("Error deleting user", e);
        }
    }

    public User findById(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToUser(resultSet);
                }
                return null;
            }
        } catch (SQLException e) {
            Logger.error(e.toString());
            throw new RuntimeException("Error finding user", e);
        }
    }

    public List<User> search(String keyword) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE username LIKE ? OR email LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            String searchValue = "%" + keyword + "%";
            statement.setString(1, searchValue);
            statement.setString(2, searchValue);
            try (ResultSet resultSet = statement.executeQuery()) {
                return mapResultSetToUsers(resultSet);
            }
        } catch (SQLException e) {
            Logger.error(e.toString());
            throw new RuntimeException("Error searching users", e);
        }
    }

    public List<User> all() {
        String query = "SELECT * FROM " + TABLE_NAME;
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            return mapResultSetToUsers(resultSet);
        } catch (SQLException e) {
            Logger.error(e.toString());
            throw new RuntimeException("Error retrieving all users", e);
        }
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setPassword(resultSet.getString("password"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setTimestamp(resultSet.getString("timestamp"));
        return user;
    }

    private List<User> mapResultSetToUsers(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(mapResultSetToUser(resultSet));
        }
        return users.isEmpty() ? Collections.emptyList() : users;
    }

    private void setUserParameters(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getPassword());
    }
}
