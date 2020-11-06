package com.JohnBurnsDev.ChatApp.Controller;

import com.JohnBurnsDev.ChatApp.Entity.ChatMessage;
import com.JohnBurnsDev.ChatApp.Service.ChatService;
import com.JohnBurnsDev.ChatApp.Storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/createRoom/{userName}")
    public ResponseEntity createRoom(@PathVariable String userName) {
        return chatService.createRoom(userName);
    }

    @MessageMapping("/chat/{to}")
    public void sendPersonalMessage(@DestinationVariable String to, @Payload ChatMessage chatMessage) throws Exception {
        System.out.println("Handling personal message: "+chatMessage+" to "+to);
        chatService.sendPersonalMessage(to, chatMessage);
    }

    @MessageMapping("/room/{roomCode}")
    public void sendRoomMessage(@DestinationVariable String roomCode, @Payload ChatMessage chatMessage) throws Exception {
        System.out.println("Handling room message: "+chatMessage+" to "+roomCode);
        chatService.sendRoomMessage(roomCode, chatMessage);
    }
}
