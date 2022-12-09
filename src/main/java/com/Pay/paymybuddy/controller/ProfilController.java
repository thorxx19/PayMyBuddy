package com.Pay.paymybuddy.controller;


import com.Pay.paymybuddy.model.AuthResponse;
import com.Pay.paymybuddy.model.Profil;
import com.Pay.paymybuddy.service.ProfilService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@CrossOrigin("http://localhost:3000/")
public class ProfilController {


    @Autowired
    private ProfilService profilService;

    /**
     * methode pour récup tout les profil
     *
     * @return une liste de profil
     */
    @GetMapping("/clients")
    public List<Profil> getClients(){
        return profilService.getClient();
    }
    /**
     * methode pour récup un profil avec sont id
     *
     * @return un profil
     */
    @GetMapping("/client")
    public List<Profil> getClient(){
        return profilService.getClientById();
    }

    /**
     * methode pour delete un client
     *
     * @param id l'id du client a dlete
     * @return 200
     * @throws ServiceException exception
     */
    @DeleteMapping("/client")
    public ResponseEntity<AuthResponse> deleteClient(@RequestParam UUID id) throws ServiceException {
       return profilService.deleteClient(id);
    }

    /**
     * Method pour recup un client en fonction de sont id
     *
     * @param id l'id reçu du front
     * @return les info du profil
     */
    @GetMapping("/clientId")
    public ResponseEntity<AuthResponse> getClientById(@RequestParam UUID id){
        return profilService.getclientById(id);
    }

}
