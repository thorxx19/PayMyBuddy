package com.Pay.paymybuddy.controller;


import com.Pay.paymybuddy.model.AuthResponse;
import com.Pay.paymybuddy.model.PostTransfert;
import com.Pay.paymybuddy.model.Transfer;
import com.Pay.paymybuddy.service.TransfertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@Slf4j
@CrossOrigin("http://localhost:3000/")
public class TransfertController {


    @Autowired
    private TransfertService transfertService;

    /**
     * methode pour échanger de l'argent entre profil
     *
     * @param postTransfert object
     * @return 200 ou 400
     */
    @PostMapping("/transfert")
    public ResponseEntity<AuthResponse> postTransfert(@Valid @RequestBody PostTransfert postTransfert){
        return transfertService.transfert(postTransfert);
    }

    /**
     * methode pour récup les transfert réaliser
     *
     * @return une liste de transfert
     */
    @GetMapping("/transferts")
    public ResponseEntity<AuthResponse> getTransfertById(){
        return transfertService.getTransfertById();
    }

    /**
     * methode pour récup le dernier transfert entre profil
     *
     * @return un transfert
     */
    @GetMapping("/transfert")
    public List<Transfer> getFirstTrasnfert(){
        return transfertService.getFirstTrasnfert();
    }

}
