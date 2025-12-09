package com.ejemplo.jwtdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class JwtIconoResponse {
    private int code;
    private String msg;
    private String jwtToken;
    // Constructor vacío
    public JwtIconoResponse() {}
    // Constructor
    public JwtIconoResponse(int code, String msg, String jwtToken) {
        this.code = code;
        this.msg = msg;
        this.jwtToken = jwtToken;
    }
    // Getters y Setters
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    public String getJwtToken() { return jwtToken; }
    public void setJwtToken(String jwtToken) { this.jwtToken = jwtToken; }
}
