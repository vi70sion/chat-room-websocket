package com.example.chatroom.repository;

import com.example.chatroom.model.User;
import java.sql.*;
import static com.example.chatroom.Constants.*;

public class UserRepository {

    private static Connection _connection;

    public UserRepository() {
    }

    public long checkClient(User user) {
        try {
            sqlConnection();
            String sql = "SELECT * FROM users WHERE name = ? AND password = ?";
            PreparedStatement statement = _connection.prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            ResultSet resultSet = statement.executeQuery();
            return (resultSet.next()) ? resultSet.getLong("user_id"): -1;
        } catch (SQLException e) {
            return -1;
        }
    }

    public static void sqlConnection() {
        try {
            _connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            // connection issues
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // handle any other exceptions
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
