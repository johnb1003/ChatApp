package com.JohnBurnsDev.ChatApp.Service;

import com.JohnBurnsDev.ChatApp.Entity.ChatMessage;
import com.JohnBurnsDev.ChatApp.Entity.ChatRoom;
import com.JohnBurnsDev.ChatApp.Storage.ChatRoomStorage;
import com.JohnBurnsDev.ChatApp.Storage.UserStorage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChatService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public ResponseEntity createRoom(String userName) {
        if(this.userExists(userName)) {
            // Create ChatRoom
            ObjectNode newRoom = objectMapper.createObjectNode();
            ChatRoom room = new ChatRoom(userName);
            ChatRoomStorage.getInstance().updateRoom(room.getRoomCode(), room);
            newRoom.put("roomCode", room.getRoomCode());
            System.out.println("Created room: "+room.getRoomCode());
            return ResponseEntity.ok().body(newRoom);
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    public void sendPersonalMessage(String to, ChatMessage chatMessage) throws Exception {
        if(this.userExists(to)) {
            messagingTemplate.convertAndSend("/topic/messages/"+to, chatMessage);
        }
    }

    public void sendRoomMessage(String roomCode, ChatMessage chatMessage) throws Exception {
        if(this.roomExists(roomCode)) {
            ArrayList<ChatMessage> messages =
                    ChatRoomStorage.getInstance().getRooms().get(roomCode).addMessage(chatMessage);
            messagingTemplate.convertAndSend("/topic/messages/"+roomCode, messages);
        }
    }

    private boolean userExists(String user) {
        return UserStorage.getInstance().getUsers().contains(user);
    }

    private boolean roomExists(String roomCode) {
        return ChatRoomStorage.getInstance().getRooms().containsKey(roomCode);
    }
}
