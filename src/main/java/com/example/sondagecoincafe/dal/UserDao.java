package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> getById(int id);
    Optional<User> selectByUsername(String username);
    void insertUser(User user);
}
