package com.example.chatroom.service;

import com.example.chatroom.JwtDecoder;
import io.jsonwebtoken.JwtException;

public class TokenService {

    public static boolean validateToken(String token){
        try {
            JwtDecoder.decodeJwt(token);
        } catch (JwtException e) {
            return false;
        }
        return true;
    }


}
