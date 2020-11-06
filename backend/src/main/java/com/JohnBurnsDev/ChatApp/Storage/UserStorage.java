package com.JohnBurnsDev.ChatApp.Storage;

import java.util.HashSet;
import java.util.Set;

public class UserStorage {
    private static UserStorage storage;
    private Set<String> users;

    public UserStorage() {
        users = new HashSet<String>();
    }

    public static synchronized UserStorage getInstance() {
        if(storage == null) {
            storage = new UserStorage();
        }
        return storage;
    }

    public Set<String> getUsers() {
        return users;
    }

    public boolean setUser(String userName) {
        if(users.contains(userName)) {
            return false;
        }
        users.add(userName);
        return true;
    }
}
