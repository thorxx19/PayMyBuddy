package com.Pay.PayMyBuddy.controller.profil;


import com.Pay.PayMyBuddy.model.AuthResponse;
import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.repository.AccountRepository;
import com.Pay.PayMyBuddy.repository.ProfilRepository;
import com.Pay.PayMyBuddy.service.ProfilService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@CrossOrigin("http://localhost:3000/")
public class ProfilController {

    @Autowired
    private ProfilRepository profilRepository;
    @Autowired
    private ProfilService profilService;

    /**
     * methode pour récup tout les profil
     * @return une liste de profil
     */
    @GetMapping("/clients")
    public List<Profil> getClients(){
        return profilService.getClient();
    }
    /**
     * methode pour récup un profil avec sont id
     * @return un profil
     */
    @GetMapping("/client")
    public List<Profil> getClient(){
        return profilService.getClientById();
    }

    /**
     * methode pour delete un client
     * @param id l'id du client a dlete
     * @return 200
     * @throws ServiceException exception
     */
    @DeleteMapping("/client")
    public ResponseEntity<AuthResponse> deleteClient(@RequestParam Long id) throws ServiceException {
       return profilService.deleteClient(id);
    }

    @GetMapping("/clientId")
    public ResponseEntity<AuthResponse> getclientById(@RequestParam Long id){
        return profilService.getclientById(id);
    }

}
