package com.Pay.PayMyBuddy.controller.login;


import com.Pay.PayMyBuddy.jwt.JwtTokenProvider;
import com.Pay.PayMyBuddy.model.AuthResponse;
import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.service.AuthService;
import com.Pay.PayMyBuddy.service.ProfilService;
import com.Pay.PayMyBuddy.model.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@Slf4j
public class AuthController {


    @Autowired
    private ProfilService profilService;

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
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody Profil profil, Errors errors){
        return authService.register(profil, errors);
    }
}
