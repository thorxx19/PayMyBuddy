package com.Pay.PayMyBuddy.controller.login;


import com.Pay.PayMyBuddy.jwt.JwtTokenProvider;
import com.Pay.PayMyBuddy.model.AuthResponse;
import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.service.AccountService;
import com.Pay.PayMyBuddy.service.ProfilService;
import com.Pay.PayMyBuddy.service.UserRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private ProfilService profilService;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AccountService accountService;


    public AuthController(AuthenticationManager authenticationManager, ProfilService profilService, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider){
        this.authenticationManager =  authenticationManager;
        this.profilService = profilService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @PostMapping("/login")
    public AuthResponse login(@RequestBody UserRequest loginRequest){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getName(), loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);
        Profil profil = profilService.getOneUserByUserName(loginRequest.getName());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken("Bearer " + jwtToken);
        authResponse.setUserId(profil.getId());

        return authResponse;
    }



    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody Profil profil){
        AuthResponse authResponse = new AuthResponse();
        if(profilService.getOneUserByUserName(profil.getName()) != null) {
            authResponse.setMessage("Username already in use.");
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        } else {
            return accountService.addProfil(profil);
        }
    }
}
