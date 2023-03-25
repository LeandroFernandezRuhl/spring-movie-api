package com.example.springmovieapi.security.payload;

/**
 * This class represents the request object sent by the client when a user attempts to login.
 * It contains two fields username and password, which hold the user's credentials.
 */
public class LoginRequest {

    private String username;
    private String password;

    public LoginRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
