package com.example.gestioninventairesystem.Repository.Impl;


import com.example.gestioninventairesystem.Config.Database;
import com.example.gestioninventairesystem.Logger.Logger;
import com.example.gestioninventairesystem.Model.User;
import com.example.gestioninventairesystem.Repository.JdbcRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthRepository implements JdbcRepository<User> {

    private static final String TABLE_NAME = "users";

    private final Connection connection;

    public AuthRepository() {
        this.connection = Database.connection();
    }

    public boolean checkByEmail(String email) {
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            Logger.error(e.toString());
            throw new RuntimeException("Error checking user by email " + email);
        }
        return false;
    }

    public User getUserByEmail(String email) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToUser(resultSet);
                }
            }
        } catch (SQLException e) {
            Logger.error(e.toString());
            throw new RuntimeException("Error finding user by username and email", e);
        }
        return null;
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

}
