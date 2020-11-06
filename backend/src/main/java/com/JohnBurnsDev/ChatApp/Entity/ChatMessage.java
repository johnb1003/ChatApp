package com.JohnBurnsDev.ChatApp.Entity;

import lombok.Getter;
import lombok.Setter;

public class ChatMessage {
    private String message;
    private String fromLogin;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromLogin() {
        return fromLogin;
    }

    public void setFromLogin(String fromLogin) {
        this.fromLogin = fromLogin;
    }
}
