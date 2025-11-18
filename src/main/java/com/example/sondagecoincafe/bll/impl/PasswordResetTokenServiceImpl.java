package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.PasswordResetTokenService;
import com.example.sondagecoincafe.configuration.security.PasswordResetToken;
import com.example.sondagecoincafe.dal.PasswordResetTokenDao;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenDao passwordResetTokenDao;

    public PasswordResetTokenServiceImpl(PasswordResetTokenDao passwordResetTokenDao) {
        this.passwordResetTokenDao = passwordResetTokenDao;
    }

    public Optional<PasswordResetToken> findByToken(String token) {
        return passwordResetTokenDao.findByToken(token);
    }

    @Override
    public void delete(PasswordResetToken prt) {
        passwordResetTokenDao.delete(prt);
    }

    @Override
    public void savePasswordResetTokenRepo(PasswordResetToken prt) {
        passwordResetTokenDao.save(prt);
    }
}
