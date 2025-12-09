package com.ejemplo.jwtdemo.service;

import com.ejemplo.jwtdemo.dto.JwtRequest;
import com.ejemplo.jwtdemo.entity.JwtClaims;

public interface JwtTokenService {
    String generateJwt(JwtClaims claims);

    String generateJwt(JwtRequest request);
}
