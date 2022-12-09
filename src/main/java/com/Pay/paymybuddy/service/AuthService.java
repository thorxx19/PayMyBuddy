package com.Pay.paymybuddy.service;

import com.Pay.paymybuddy.jwt.JwtTokenProvider;
import com.Pay.paymybuddy.model.AuthResponse;
import com.Pay.paymybuddy.model.Profil;
import com.Pay.paymybuddy.model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private ProfilService profilService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    /**
     * Methode pour gerer le login coter front
     *
     * @param loginRequest l'object reçu
     * @return le token
     */
    public ResponseEntity<AuthResponse> login(UserRequest loginRequest) {


            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getMail(), loginRequest.getPassword());
            Authentication auth = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
            String jwtToken = jwtTokenProvider.generateJwtToken(auth);
            Profil profil = profilService.getOneUserByUserMail(loginRequest.getMail());
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("Votre token");
            authResponse.setAccessToken("Bearer " + jwtToken);
            authResponse.setUserId(profil.getId());
            return new ResponseEntity<>(authResponse,HttpStatus.OK);

    }

    /**
     * methode pour vérifier si un profil existe déja avec le même mail
     *
     * @param profil le profil a controler
     * @return 201 ou 400
     */
    public ResponseEntity<AuthResponse> register(Profil profil) {
        AuthResponse authResponse = new AuthResponse();

        if(profilService.getProfilByMail(profil.getMail()) != null) {
            authResponse.setMessage("Username already in use.");
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }
               return accountService.addProfil(profil);
        }
    }



