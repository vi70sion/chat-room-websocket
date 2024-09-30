package com.example.chatroom.repository;

import com.example.chatroom.model.ChatMessage;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.chatroom.Constants.*;

public class MessageRepository {

    private static Connection _connection;
    List<ChatMessage> messagesList;
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

    public List<ChatMessage> getLastTenMesegesList(){
        try {
            ChatMessage message;
            sqlConnection();
            messagesList = new ArrayList<>();
            String sql = "SELECT * FROM ( SELECT * FROM messages ORDER BY timestamp DESC LIMIT 10 ) AS last_ten ORDER BY timestamp ASC";
            PreparedStatement statement = _connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                message = new ChatMessage();
                message.setSender(resultSet.getString("user_name"));
                message.setContent(resultSet.getString("message_content"));
                message.setSentAt(LocalDateTime.parse(resultSet.getString("timestamp"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                messagesList.add(message);
            }
            return messagesList;
        } catch (SQLException e) {
            return new ArrayList<>();
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
