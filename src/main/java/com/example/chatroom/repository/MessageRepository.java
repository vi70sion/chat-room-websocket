package com.example.chatroom.repository;

import com.example.chatroom.model.ChatMessage;
import com.example.chatroom.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.example.chatroom.Constants.*;

public class MessageRepository {

    private static Connection _connection;
    public MessageRepository() {
    }

    public boolean addMessage(ChatMessage message) {
        try {
            sqlConnection();
            String sql = "INSERT INTO messages (user_name, message_content, timestamp) VALUES (?,?,?)";
            PreparedStatement statement = _connection.prepareStatement(sql);
            statement.setString(1, message.getSender());
            statement.setString(2, message.getContent());
            statement.setString(3, message.getSentAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0 ? true : false;
        } catch (SQLException e) {
            return false;
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
