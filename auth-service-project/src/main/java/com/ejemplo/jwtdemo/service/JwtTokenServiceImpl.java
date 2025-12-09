package com.ejemplo.jwtdemo.service;

import com.ejemplo.jwtdemo.dto.JwtRequest;
import com.ejemplo.jwtdemo.entity.JwtClaims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration}")
    private long expirationMs;

    @Override
    public String generateJwt(JwtClaims claims) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(12 * 60 * 60); // 12 horas en segundos

        claims.setIat(now.getEpochSecond());
        claims.setExp(expiration.getEpochSecond());

        return Jwts.builder()
                .setIssuer(claims.getIss())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .setAudience(claims.getAud())
                .setSubject(claims.getSub())
                .claim("servicio", claims.getServicio())
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateJwt(JwtRequest request) {
        // Validar tokenPublic (opcional: decodificar y verificar)
        // Aquí asumimos que es válido; puedes agregar lógica para parsear el JWT

        Instant now = Instant.now();
        Instant expiration = now.plusMillis(expirationMs);

        // Hardcodear userInfo y roles basados en el ejemplo
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", 23019);
        userInfo.put("userCode", "");
        userInfo.put("userName", request.getUsername()); // Usar username del request
        userInfo.put("codArea", null);
        userInfo.put("area", null);
        userInfo.put("siglaSector", null);
        userInfo.put("codSector", null);
        userInfo.put("sector", null);
        userInfo.put("idInspector", 0);
        userInfo.put("codigoInspector", "");
        userInfo.put("servicio", "AGC");
        userInfo.put("idSistema", 0);
        userInfo.put("idOperario", null);
        userInfo.put("codigoCarga", "gnr");

        List<Map<String, Object>> roles = List.of(
                Map.of("idRol", 329, "rol", "CONSCOM", "acronimo", "CONSULTA COM"),
                Map.of("idRol", 62, "rol", "GOM11", "acronimo", "JS. CENTRO OPERACIONES E INFORMACION")
        );

        String token = Jwts.builder()
                .setSubject(request.getUsername())
                .setIssuer("https://iconorest.et.bo")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .claim("userInfo", userInfo)
                .claim("roles", roles)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();

        // Opcional: Guardar en DB
        //tokenRepository.save(new TokenEntity(token));

        return token;
    }

}
