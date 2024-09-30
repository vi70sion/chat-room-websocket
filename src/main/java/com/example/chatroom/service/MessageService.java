package com.example.chatroom.service;

import com.example.chatroom.model.ChatMessage;
import com.example.chatroom.repository.MessageRepository;

import java.util.List;

public class MessageService {

    MessageRepository messageRepository = new MessageRepository();

    public boolean addMessage(ChatMessage message){
        return messageRepository.addMessage(message);
    }

    public List<ChatMessage> getLastTenMesegesList(){
        return messageRepository.getLastTenMesegesList();
    }

}
