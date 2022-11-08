package com.Pay.PayMyBuddy.service;

import com.Pay.PayMyBuddy.jwt.JwtTokenProvider;
import com.Pay.PayMyBuddy.model.AuthResponse;
import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

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
        Profil profil = profilService.getOneUserByUserName(loginRequest.getMail());
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
    public ResponseEntity<AuthResponse> register(Profil profil, Errors errors){
        AuthResponse authResponse = new AuthResponse();

        if(profilService.getProfilByMail(profil.getMail()) != null) {
            authResponse.setMessage("Username already in use.");
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        } else {
            if (profil.getName().equals("")
                    || profil.getPassword().equals("")
                    || profil.getLastName().equals("")
                    || profil.getMail().equals("")
                    || errors.hasErrors()) {
                authResponse.setMessage("Mdp ou password non valide");
                return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
            } else {
                accountService.addProfil(profil);
                return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
            }
            }
        }
    }



