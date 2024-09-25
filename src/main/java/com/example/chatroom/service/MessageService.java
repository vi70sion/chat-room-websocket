package com.example.chatroom.service;

import com.example.chatroom.model.ChatMessage;
import com.example.chatroom.repository.MessageRepository;

public class MessageService {

    MessageRepository messageRepository = new MessageRepository();

    public boolean addMessage(ChatMessage message){
        return messageRepository.addMessage(message);
    }

}
