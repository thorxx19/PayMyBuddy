package com.Pay.PayMyBuddy.controller.login;


import com.Pay.PayMyBuddy.jwt.JwtTokenProvider;
import com.Pay.PayMyBuddy.model.AuthResponse;
import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.service.ProfilService;
import com.Pay.PayMyBuddy.model.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final ProfilService profilService;
    private final JwtTokenProvider jwtTokenProvider;



    public AuthController(AuthenticationManager authenticationManager, ProfilService profilService, JwtTokenProvider jwtTokenProvider){
        this.authenticationManager =  authenticationManager;
        this.profilService = profilService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * methode pour connecter un profil avec ces identifient
     * @param loginRequest les login du profil
     * @return un token
     */
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

    /**
     * methode pour enregister un nouveau client
     * @param profil un object
     * @return 201 ou 400
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody Profil profil){
        return profilService.getOneUsersByMail(profil);
    }
}
