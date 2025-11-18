package com.example.sondagecoincafe.bo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter @Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Le pseudo doit être alphanumérique")
    private String username;
    @NotBlank
    private String name;
    @NotBlank
    private String firstname;
    @NotBlank
    private String email;
    @NotBlank
    private String phone;
    @NotBlank
    private String street;
    @NotBlank
    private String zipCode;
    @NotBlank
    private String city;
    @NotBlank
    private String password;
    @Transient
    private String passwordConfirmation;
    @NotNull
    private boolean admin;
    private Collection<GrantedAuthority> authorities;

    public User() {
    }

    public User(Integer no_user, String username, String name, String firstname, String email, String phone, String street, String zipCode, String city, int credit, String password, String passwordConfirmation, boolean admin) {
        this.username = username;
        this.name = name;
        this.firstname = firstname;
        this.email = email;
        this.phone = phone;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.admin = admin;
    }

    public User(String username, String name, String firstname, String email, String street, String phone, String zipCode, String city, String password, String passwordConfirmation, int credit, boolean admin) {
        this.username = username;
        this.name = name;
        this.firstname = firstname;
        this.email = email;
        this.street = street;
        this.phone = phone;
        this.zipCode = zipCode;
        this.city = city;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.admin = admin;
    }

    public void addRole(String role) {
        authorities.add(
                new SimpleGrantedAuthority(role)
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // TODO: do not display password
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", firstname='" + firstname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", password='" + password + '\'' +
                ", admin=" + admin +
                '}';
    }
}
