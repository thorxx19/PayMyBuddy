package com.Pay.paymybuddy.controller;


import com.Pay.paymybuddy.model.AuthResponse;
import com.Pay.paymybuddy.model.Profil;
import com.Pay.paymybuddy.service.AuthService;
import com.Pay.paymybuddy.model.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@Slf4j
public class AuthController {


    @Autowired
    private AuthService authService;


    /**
     * methode pour connecter un profil avec ces identifient
     *
     * @param loginRequest les login du profil
     * @return un token
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserRequest loginRequest){
        return authService.login(loginRequest);
    }

    /**
     * methode pour enregister un nouveau client
     *
     * @param profil un object
     * @return 201 ou 400
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody Profil profil)  {
        return authService.register(profil);
    }
}
