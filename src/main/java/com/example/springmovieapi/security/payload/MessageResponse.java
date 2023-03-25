package com.example.springmovieapi.security.payload;

/**
 * This class represents a generic response object that can be sent back to the client in case of various events or errors.
 * It contains a single field message, which holds the message that will be displayed to the user.
 */
public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
