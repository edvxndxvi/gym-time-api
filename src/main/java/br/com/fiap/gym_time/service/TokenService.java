package br.com.fiap.gym_time.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.fiap.gym_time.models.User;
import br.com.fiap.gym_time.models.UserRole;

@Service
public class TokenService {

    private Instant expiresAt = LocalDateTime.now()
            .plusDays(1)
            .toInstant(ZoneOffset.ofHours(-3));

    private Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

    public String createToken(User user){
        return JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole() != null ? user.getRole().toString() : "UNKNOWN")
                .withExpiresAt(expiresAt)
                .sign(algorithm);
    }

    public User getUserFromToken(String jwt){
        var jwtVerifier = JWT.require(algorithm).build().verify(jwt);
        return User.builder()
                .id(Long.parseLong(jwtVerifier.getSubject()))
                .email(jwtVerifier.getClaim("email").asString())
                .role(UserRole.valueOf(jwtVerifier.getClaim("role").asString()))
                .build();
    }
}

