package com.example.chatroom.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.chatroom.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final List<ChatMessage> messages = new ArrayList<>();
    private final List<String> users = new ArrayList<>();

    @Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) {
        message.setSentAt(LocalDateTime.now());
        messages.add(message);
        return message;
    }

    @MessageMapping("/user")
    @SendTo("/topic/users")
    public List<String> addUser(String username) {
        if (!users.contains(username)) {
            users.add(username);
            messagingTemplate.convertAndSend("/topic/messages", new ChatMessage("SYSTEM", username + " has joined the chat", LocalDateTime.now()));
        }
        return users;
    }

    @MessageMapping("/leave")
    @SendTo("/topic/users")
    public List<String> removeUser(String username) {
        users.remove(username);
        messagingTemplate.convertAndSend("/topic/messages", new ChatMessage("SYSTEM", username + " has left the chat", LocalDateTime.now()));
        return users;
    }


}




