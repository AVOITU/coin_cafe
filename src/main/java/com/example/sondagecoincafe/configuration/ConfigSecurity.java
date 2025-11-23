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

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class ConfigSecurity {


    private static final Logger log = LoggerFactory.getLogger(ConfigSecurity.class);
    public static final String SELECT_USER =
            "SELECT email AS username, password, 1 AS enabled " +
                    "FROM users WHERE email = ?";

    public static final String SELECT_ROLES =
            "SELECT email AS username, " +
                    "CASE WHEN admin = 1 THEN 'ROLE_ADMIN' ELSE 'ROLE_USER' END AS authority " +
                    "FROM users WHERE email = ?";

    @Bean
//    TODO : Decommenter la sécu et retirer le request mtchers qui autorise tout
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/login",
//                                "/forgot-password",
//                                "/reset-password",
//                                "/survey",
//                                "/css/**", "/javascript/**", "/images/**")
//                        .permitAll()
//                        .requestMatchers("/results")
//                        .hasRole("ADMIN")     // ← protection admin
//                        .anyRequest()
//                        .authenticated()
//                )
                .requestMatchers(
                        "/login",
                        "/forgot-password",
                        "/reset-password",
                        "/survey",
                        "/css/**",
                        "/javascript/**",
                        "/images/**",
                        "/results"        // toujours autorisé
                ).permitAll()
                .anyRequest().authenticated()
)
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")     // IMPORTANT
                        .passwordParameter("password")
                        .defaultSuccessUrl("/results", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
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
