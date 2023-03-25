package com.example.springmovieapi.security.payload;

/**
 * This class represents the response object sent back to the client after a successful authentication.
 * It contains a single field token which holds the JWT token that the client can use to access protected resources on the server.
 */
public class JwtResponse {

    private String token;

    public JwtResponse() {
    }
    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
