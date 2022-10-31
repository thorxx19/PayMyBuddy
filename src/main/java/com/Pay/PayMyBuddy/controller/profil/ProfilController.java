package com.Pay.PayMyBuddy.controller.profil;


import com.Pay.PayMyBuddy.model.AuthResponse;
import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.repository.AccountRepository;
import com.Pay.PayMyBuddy.repository.ProfilRepository;
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

    /**
     * methode pour récup tout les profil
     * @return une liste de profil
     */
    @GetMapping("/clients")
    public List<Profil> getClient(){
        return profilRepository.findAll();
    }
    /**
     * methode pour récup un profil avec sont id
     * @param id l'id du profil
     * @return un profil
     */
    @GetMapping("/client")
    public List<Profil> getClientById(@RequestParam long id){
        return profilRepository.findByIdList(id);
    }

    /**
     * methode pour delete un client
     * @param id l'id du client a dlete
     * @return 200
     * @throws ServiceException exception
     */
    @DeleteMapping("/client")
    public ResponseEntity<AuthResponse> deleteClient(@RequestParam long id) throws ServiceException {
        AuthResponse authResponse = new AuthResponse();
        profilRepository.deleteById(id);
        authResponse.setMessage("Profil bien delete");
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }


}
