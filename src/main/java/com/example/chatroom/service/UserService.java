package com.example.chatroom.service;

import com.example.chatroom.JwtDecoder;
import com.example.chatroom.model.User;
import com.example.chatroom.repository.UserRepository;
import io.jsonwebtoken.JwtException;

public class UserService {
    UserRepository userRepository = new UserRepository();

    public long checkClient(User user) {
        return userRepository.checkClient(user);
    }


}
