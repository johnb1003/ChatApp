package com.JohnBurnsDev.ChatApp.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

@Component
public class StompEventListener {

    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) {
        //System.out.println("Session Connected: "+event.getMessage().getPayload()+" - "+event.getUser());
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        System.out.println("Connected: " + sha.getLogin() + " - "+sha.getSessionId());
    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        System.out.println("Disconnected: " + sha.getSessionId());
    }

    @EventListener
    private void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        System.out.println("Session Subscribe: "+event.getSource().toString());
    }

    @EventListener
    private void handleSessionUnsubscribeEvent(SessionUnsubscribeEvent event) {
        System.out.println("Session Unsubscribe: "+event.getSource().toString());
    }
}
