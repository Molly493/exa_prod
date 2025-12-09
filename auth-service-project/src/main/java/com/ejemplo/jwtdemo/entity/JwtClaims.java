package com.ejemplo.jwtdemo.entity;

import java.time.Instant;
public class JwtClaims {
    private String iss;
    private long iat;
    private long exp;
    private String aud;
    private String sub;
    private String servicio;
    // Constructor vacío
    public JwtClaims() {}
    // Constructor con valores
    public JwtClaims(String iss, long iat, long exp, String aud, String sub, String servicio) {
        this.iss = iss;
        this.iat = iat;
        this.exp = exp;
        this.aud = aud;
        this.sub = sub;
        this.servicio = servicio;
    }
    // Getters y Setters
    public String getIss() { return iss; }
    public void setIss(String iss) { this.iss = iss; }
    public long getIat() { return iat; }
    public void setIat(long iat) { this.iat = iat; }
    public long getExp() { return exp; }
    public void setExp(long exp) { this.exp = exp; }
    public String getAud() { return aud; }
    public void setAud(String aud) { this.aud = aud; }
    public String getSub() { return sub; }
    public void setSub(String sub) { this.sub = sub; }
    public String getServicio() { return servicio; }
    public void setServicio(String servicio) { this.servicio = servicio; }
}
