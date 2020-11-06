package com.JohnBurnsDev.ChatApp.Controller;

import com.JohnBurnsDev.ChatApp.Storage.UserStorage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class UsersController {

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
