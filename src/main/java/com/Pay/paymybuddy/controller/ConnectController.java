package com.Pay.paymybuddy.controller;

import com.Pay.paymybuddy.model.AuthResponse;
import com.Pay.paymybuddy.model.dto.ConnectDto;
import com.Pay.paymybuddy.service.ConnectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@CrossOrigin("http://localhost:3000/")
public class ConnectController {

    @Autowired
    private ConnectService connectService;

    /**
     * methode pour connecter 2 profil
     *
     * @param connectDto un object avec l'id du débiteur et du créditeur
     * @return 202 ou 400
     */
    @PostMapping("/connect")
    public ResponseEntity<AuthResponse> postConnect(@Valid @RequestBody ConnectDto connectDto){
        return connectService.postConnect(connectDto.getIdDeux());
    }

    /**
     * methode pour récup les connection pour un profil donné
     *
     * @return une liste de profil connecter
     */
    @GetMapping("/connect")
    public ResponseEntity<AuthResponse> getConnectById() {
        return connectService.getConnectById();
    }

}
