package com.example.chat_room;

public class Message {
    private String name;

    public Message() {}

    public Message(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
