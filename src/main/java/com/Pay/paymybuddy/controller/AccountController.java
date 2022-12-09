package com.Pay.paymybuddy.controller;


import com.Pay.paymybuddy.model.*;
import com.Pay.paymybuddy.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin("http://localhost:3000/")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * methode pour récupérer les profils pas connecter avec le profil.id
     *
     * @return une liste de profil
     */
    @GetMapping("/mail")
    public List<Profil> getConnect(){
       return accountService.getConnect();
    }

    /**
     * methode pour modifier le solde d'un profil
     *
     * @param accountRequest la balance et l'id du profil
     * @return 200 ou 403
     */
    @PutMapping("/solde")
    public ResponseEntity<AuthResponse> modifSolde(@Valid @RequestBody AccountRequest accountRequest){
        return accountService.modifSolde(accountRequest.getBalance());
    }

}
