package com.example.sondagecoincafe.bo;

import jakarta.validation.constraints.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class User implements UserDetails {

    private Integer no_user;
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
    @NotBlank
    private String passwordConfirmation;
    @NotNull
    @Min(value = 0)
    private int credit;
    @NotNull
    private boolean admin;
    private Collection<GrantedAuthority> authorities;

    public User() {
    }

    public User(Integer no_user, String username, String name, String firstname, String email, String phone, String street, String zipCode, String city, int credit, String password, String passwordConfirmation, boolean admin) {
        this.no_user = no_user;
        this.username = username;
        this.name = name;
        this.firstname = firstname;
        this.email = email;
        this.phone = phone;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.credit = credit;
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
        this.credit = credit;
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

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Integer getNo_user() {
        return no_user;
    }

    public void setNo_user(Integer no_user) {
        this.no_user = no_user;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() { return passwordConfirmation; }

    public void setPasswordConfirmation(String passwordConfirmation) { this.passwordConfirmation = passwordConfirmation; }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    // TODO: do not display password
    @Override
    public String toString() {
        return "User{" +
                "no_user=" + no_user +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", firstname='" + firstname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", password='" + password + '\'' +
                ", credit=" + credit +
                ", admin=" + admin +
                '}';
    }
}
