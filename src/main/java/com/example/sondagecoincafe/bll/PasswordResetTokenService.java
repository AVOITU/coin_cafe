package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.configuration.security.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenService {
    Optional<PasswordResetToken> findByToken(String token);

    void delete(PasswordResetToken prt);

    void savePasswordResetTokenRepo(PasswordResetToken prt);
}
