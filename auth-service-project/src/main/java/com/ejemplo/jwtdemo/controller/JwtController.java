package com.ejemplo.jwtdemo.controller;

import com.ejemplo.jwtdemo.dto.JwtIconoResponse;
import com.ejemplo.jwtdemo.dto.JwtRequest;
import com.ejemplo.jwtdemo.entity.JwtClaims;
import com.ejemplo.jwtdemo.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/jwt")
public class JwtController {
    private final JwtTokenService jwtTokenService;

    public JwtController(@Autowired JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @GetMapping("/generate")
    public ResponseEntity<String> generateJwt() {
        // Usar los valores del ejemplo proporcionado
        JwtClaims claims = new JwtClaims(
                "EMPRESA", // iss
                0, // iat se calcula en el service
                0, // exp se calcula en el service
                "emp.bo", // aud
                "EMPRESA", // sub
                "AGC" // servicio
        );
        String token = jwtTokenService.generateJwt(claims);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateJwt(@RequestBody JwtRequest request) {
        String token = jwtTokenService.generateJwt(request);
        JwtIconoResponse response = new JwtIconoResponse(0, "Proceso realizado correctamente", token);
        return ResponseEntity.ok(response);
    }
}
