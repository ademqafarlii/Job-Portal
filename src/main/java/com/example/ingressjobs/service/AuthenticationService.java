package com.example.ingressjobs.service;


import com.example.ingressjobs.dto.auth.AuthenticationRequest;
import com.example.ingressjobs.dto.auth.AuthenticationResponse;
import com.example.ingressjobs.dto.auth.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest registerRequest);

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
