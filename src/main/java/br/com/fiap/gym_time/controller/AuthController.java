package br.com.fiap.gym_time.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.gym_time.models.Credentials;
import br.com.fiap.gym_time.dto.Token;
import br.com.fiap.gym_time.models.User;
import br.com.fiap.gym_time.service.AuthService;
import br.com.fiap.gym_time.service.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public Token login(@RequestBody Credentials credentials) {
        User user = (User) authService.loadUserByUsername(credentials.email());
        if(!passwordEncoder.matches(credentials.password(), user.getPassword())) {
            throw new RuntimeException("Senha incorreta");
        }
        String jwt = tokenService.createToken(user);
        
        return new Token(jwt);
    }
    
}
