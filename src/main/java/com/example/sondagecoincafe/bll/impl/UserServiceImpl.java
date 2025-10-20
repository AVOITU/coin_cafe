package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.UserService;
import com.example.sondagecoincafe.bo.User;
import com.example.sondagecoincafe.dal.UserDao;
import com.example.sondagecoincafe.exceptions.BusinessCode;
import com.example.sondagecoincafe.exceptions.BusinessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> getById(int id) {
        return userDao.getById(id);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userDao.selectByUsername(username);
    }

    @Override
    public void createUser(User user) {
        BusinessException businessException = new BusinessException();
        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            businessException.addKey(BusinessCode.VALIDATION_USER_PASSWORD_CONFIRMATION);
            throw businessException;
        }
        user.setPassword(
                this.passwordEncoder.encode(user.getPassword())
        );
        userDao.insertUser(user);
    }
}
