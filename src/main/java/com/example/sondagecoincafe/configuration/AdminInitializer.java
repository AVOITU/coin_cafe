package com.example.sondagecoincafe.configuration;

import com.example.sondagecoincafe.bo.User;
import com.example.sondagecoincafe.dal.UserDao;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    private final UserDao userDao;

    public AdminInitializer(UserDao userDao) {
        this.userDao = userDao;
    }

    @Bean
    CommandLineRunner init(UserDao userDao, PasswordEncoder encoder) {
        return args -> {
            if (userDao.count() == 0) {
                User admin = new User();
                admin.setUsername("ravy");
                admin.setName("coin_cafe");
                admin.setFirstname("ravy");
                admin.setEmail("voiturona@gmail.com");
                admin.setPhone("0601020304");
                admin.setStreet("10 Rue du Test");
                admin.setZipCode("75000");
                admin.setCity("Quimp");
                // mot de passe encodé
                admin.setPassword(encoder.encode("Admin123!"));
                admin.setPasswordConfirmation("Admin123!");
                admin.setAdmin(true);

                userDao.save(admin);
            }
        };
    }
}

