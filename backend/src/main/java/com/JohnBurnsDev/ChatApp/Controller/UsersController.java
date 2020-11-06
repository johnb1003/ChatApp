package com.JohnBurnsDev.ChatApp.Controller;

import com.JohnBurnsDev.ChatApp.Storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class UsersController {

    @Autowired
    SimpUserRegistry simpUserRegistry;

    @GetMapping("/register/{userName}")
    public ResponseEntity<Void> register(@PathVariable String userName) {
        if(UserStorage.getInstance().setUser(userName)) {
            System.out.println("Added "+userName);
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/fetchAllUsers")
    public Set<String> fetchAll() {
        return UserStorage.getInstance().getUsers();
    }
}
