package com.Pay.PayMyBuddy.controller.transfert;


import com.Pay.PayMyBuddy.model.AuthResponse;
import com.Pay.PayMyBuddy.model.PostTransfert;
import com.Pay.PayMyBuddy.model.Transfer;
import com.Pay.PayMyBuddy.repository.TransferRepository;
import com.Pay.PayMyBuddy.service.TransfertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@Slf4j
@CrossOrigin("http://localhost:3000/")
public class TransfertController {


    @Autowired
    private TransfertService transfertService;

    /**
     * methode pour transferais de l'argent entre profil
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
