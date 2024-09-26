package com.example.chatroom.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.chatroom.model.ChatMessage;
import com.example.chatroom.service.MessageService;
import com.example.chatroom.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    MessageService messageService = new MessageService();
    private final List<ChatMessage> messages = new ArrayList<>();
    private final List<String> users = new ArrayList<>();

    @Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
        String token = headerAccessor.getFirstNativeHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
            boolean isValid = TokenService.validateToken(token);
            if (!isValid) {
                throw new RuntimeException("Invalid JWT Token");
            }

            // Optionally, extract user details from the token
//            String usernameFromToken = jwtUtil.extractUsername(token);
//            message.setSender(usernameFromToken); // Override sender with verified token user
        } else {
            throw new RuntimeException("Authorization header not found");
        }

        message.setSentAt(LocalDateTime.now());
        messages.add(message);
        messageService.addMessage(message);
        return message;
    }

    @MessageMapping("/user")
    @SendTo("/topic/users")
    public List<String> addUser(String username) {
        if (!users.contains(username)) {
            users.add(username);
            ChatMessage message = new ChatMessage("SYSTEM", username + " has joined the chat", LocalDateTime.now());
            messageService.addMessage(message);
            messagingTemplate.convertAndSend("/topic/messages", message);
        }
        return users;
    }

    @MessageMapping("/leave")
    @SendTo("/topic/users")
    public List<String> removeUser(String username) {
        users.remove(username);
        ChatMessage message = new ChatMessage("SYSTEM", username + " has left the chat", LocalDateTime.now());
        messageService.addMessage(message);
        messagingTemplate.convertAndSend("/topic/messages", message);
        return users;
    }


}




