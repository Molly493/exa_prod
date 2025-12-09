package com.ejemplo.jwtdemo.dto;

public class JwtRequest {
    private String username;
    private String password;
    private String tokenPublic;

    // Constructor vacío
    public JwtRequest() {}

    // Constructor
    public JwtRequest(String username, String password, String tokenPublic) {
        this.username = username;
        this.password = password;
        this.tokenPublic = tokenPublic;
    }

    // Getters y Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getTokenPublic() { return tokenPublic; }
    public void setTokenPublic(String tokenPublic) { this.tokenPublic = tokenPublic; }
}