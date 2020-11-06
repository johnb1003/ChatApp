package com.JohnBurnsDev.ChatApp.Storage;

import com.JohnBurnsDev.ChatApp.Entity.ChatRoom;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ChatRoomStorage {
    private static ChatRoomStorage storage;
    private HashMap<String, ChatRoom> rooms;
    private String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                                "U", "V", "W", "X", "Y", "Z"};
    private final int CODE_LENGTH = 6;

    public ChatRoomStorage() {
        rooms = new HashMap<String, ChatRoom>();
    }

    public String generateCode() {
        String code = "";
        while(code.equals("") || this.rooms.containsKey(code)) {
            code = "";
            for(int i=0; i<CODE_LENGTH; i++) {
                code += letters[(int)Math.floor(Math.random() * 26)];
            }
        }
        return code;
    }

    public static synchronized ChatRoomStorage getInstance() {
        if(storage == null) {
            storage = new ChatRoomStorage();
        }
        return storage;
    }

    public HashMap<String, ChatRoom> getRooms() {
        return rooms;
    }

    public void updateRoom(String roomCode, ChatRoom chatRoom) {
        rooms.put(roomCode, chatRoom);
    }
}
