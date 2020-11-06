package com.JohnBurnsDev.ChatApp.Entity;

import com.JohnBurnsDev.ChatApp.Storage.ChatRoomStorage;
import lombok.Getter;

import java.util.ArrayList;

public class ChatRoom {
    private String roomCode;
    private String hostID;
    private ArrayList<String> users;
    private ArrayList<ChatMessage> messages;

    public ChatRoom(String hostID) {
        this.roomCode = ChatRoomStorage.getInstance().generateCode();
        this.hostID = hostID;
        this.users = new ArrayList<String>();
        this.users.add(hostID);
        this.messages = new ArrayList<ChatMessage>();
    }

    public String getRoomCode() {
        return roomCode;
    }

    public String getHost() {
        return hostID;
    }

    public ArrayList<ChatMessage> getMessages() {
        return messages;
    }

    public ArrayList<ChatMessage> addMessage(ChatMessage message) {
        this.messages.add(message);
        return this.messages;
    }
}
