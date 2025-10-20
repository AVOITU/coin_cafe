package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.bo.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getById(int id);
    Optional<User> getByUsername(String username);
    void createUser(User user);
}
