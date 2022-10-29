package com.Pay.PayMyBuddy.service;


import com.Pay.PayMyBuddy.model.AuthResponse;
import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.repository.ProfilRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
public class ProfilService {


    @Autowired
    private AccountService accountService;
    @Autowired
    private ProfilRepository profilRepository;

    /**
     * methode pour récup le profil du débiteur
     * @param id du débiteur
     * @param balance l'argent a enlever a sont compte
     * @return profil
     */
    @Transactional(readOnly = true)
    public Profil getProfilDebtor(Long id, BigDecimal balance){
        Profil profil = profilRepository.findByProfilId(id);
        BigDecimal solde = new BigDecimal(String.valueOf(profil.getAccountId().getBalance()));
        if (solde.intValue() >= balance.intValue()) {
            profil.getAccountId().setBalance(solde.subtract(balance));
            profilRepository.save(profil);
            return profil;
        }
        return null;
    }

    /**
     * methode pour récup le profil du débiteur
     * @param id du créditeur
     * @param balance l'argenet a créditer au profil
     * @return profil
     */
    @Transactional(readOnly = true)
    public Profil getProfilCredit(Long id, BigDecimal balance){
        Profil profil = profilRepository.findByProfilId(id);
        BigDecimal solde = new BigDecimal(String.valueOf(profil.getAccountId().getBalance())) ;
        profil.getAccountId().setBalance(solde.add(balance));
        profilRepository.save(profil);
        return profil;
    }

    /**
     * methode pour vérifier si un profil existe
     * @param id du profila vérifié
     * @return le profil ou null
     */
    @Transactional(readOnly = true)
    public Profil getProfil(Long id){
        if (profilRepository.existsById(id)) {
            return profilRepository.findByProfilId(id);
        } else {
            return null;
        }
    }

    /**
     * methode pour vérifier si un profil existe déja avec le même mail
     * @param profil le profil a controler
     * @return 201 ou 400
     */
    public ResponseEntity<AuthResponse> getOneUsersByMail(Profil profil){
        AuthResponse authResponse = new AuthResponse();

        if(getProfilByMail(profil.getMail()) != null) {
            authResponse.setMessage("Username already in use.");
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        } else {
            if (profil.getName().equals("") || profil.getPassword().equals("") || profil.getLastName().equals("") || profil.getMail().equals("")){
                return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
            } else {
                accountService.addProfil(profil);
                return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
            }

        }
    }

    /**
     * methode récup un profil avec sont mail
     * @param mail mail du profil
     * @return le profil
     */
    public Profil getProfilByMail(String mail){
        return profilRepository.findByMail(mail);
    }

    /**
     * methode récup un profil avec son nom
     * @param name du profil
     * @return le profil
     */
    public Profil getOneUserByUserName(String name){
        return profilRepository.findByName(name);
    }
}
