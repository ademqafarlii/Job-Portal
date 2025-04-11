package com.example.ingressjobs.controller;

import com.example.ingressjobs.aop.customAnnotation.ConsoleLog;
import com.example.ingressjobs.dto.auth.AuthenticationRequest;
import com.example.ingressjobs.dto.auth.AuthenticationResponse;
import com.example.ingressjobs.dto.auth.RegisterRequest;
import com.example.ingressjobs.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    @ConsoleLog(value = "user registration")
    public AuthenticationResponse register(@RequestBody RegisterRequest registerRequest) {
        return authenticationService.register(registerRequest);
    }

    @PostMapping("/authenticate")
    @ConsoleLog(value = "login")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationService.authenticate(authenticationRequest);
    }
}
