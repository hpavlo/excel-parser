package com.example.excelparser.controller;

import com.example.excelparser.dto.request.LoginRequest;
import com.example.excelparser.dto.request.RegisterRequest;
import com.example.excelparser.dto.response.JwtResponse;
import com.example.excelparser.dto.response.MessageResponse;
import com.example.excelparser.service.AuthenticationService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        String login = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        if (authenticationService.usernameAvailabilityCheck(login)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Username is already taken!"));
        }
        authenticationService.register(login, password);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String login = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        String jwt = authenticationService.login(login, password);
        List<String> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return ResponseEntity.ok(new JwtResponse(jwt, login, roles));
    }
}
