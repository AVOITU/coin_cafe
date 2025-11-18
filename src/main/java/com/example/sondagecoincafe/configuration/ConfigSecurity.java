package com.example.sondagecoincafe.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class ConfigSecurity {


    private static final Logger log = LoggerFactory.getLogger(ConfigSecurity.class);
    public static final String SELECT_USER = "SELECT pseudo, mot_de_passe, 1 FROM UTILISATEURS WHERE pseudo = ?";
    public static final String SELECT_ROLES = "SELECT u.pseudo, r.ROLE FROM UTILISATEURS AS u INNER JOIN ROLES AS r on u.administrateur = r.IS_ADMIN WHERE pseudo = ?";

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers("/mon-compte/**").authenticated();
                            auth.anyRequest().permitAll();
                        }
                )
                .formLogin(
                        login -> {
                            login.loginPage("/login");
                            login.failureUrl("/login?error");
                            login.defaultSuccessUrl("/stats").permitAll();
                        }
                ).logout(logout -> {
                    logout
                            .invalidateHttpSession(true)
                            .clearAuthentication(true)
                            .deleteCookies("JSESSIONID")
                            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                            .logoutSuccessUrl("/survey")
                            .permitAll();
                })
                .build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery(SELECT_USER);
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(SELECT_ROLES);

        return jdbcUserDetailsManager;
    }
}
