package com.ejemplo.jwtdemo.repository;

import com.ejemplo.jwtdemo.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    // Métodos adicionales si es necesario, ej. findByToken(String token)
}
