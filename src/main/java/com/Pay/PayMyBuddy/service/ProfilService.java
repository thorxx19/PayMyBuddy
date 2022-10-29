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
    ProfilRepository profilRepository;
    @Transactional(readOnly = true)
    public Profil getProfilDebtor(Long id, BigDecimal balance){
        Profil profil = profilRepository.findByProfilId(id);
        BigDecimal solde = new BigDecimal(String.valueOf(profil.getAccountId().getBalance())) ;
        profil.getAccountId().setBalance(solde.subtract(balance));
        profilRepository.save(profil);
        return profil;
    }
    @Transactional(readOnly = true)
    public Profil getProfilCredit(Long id, BigDecimal balance){
        Profil profil = profilRepository.findByProfilId(id);
        BigDecimal solde = new BigDecimal(String.valueOf(profil.getAccountId().getBalance())) ;
        profil.getAccountId().setBalance(solde.add(balance));
        profilRepository.save(profil);
        return profil;
    }
    @Transactional(readOnly = true)
    public Profil getProfil(Long id){
        if (profilRepository.existsById(id)) {
            return profilRepository.findByProfilId(id);
        } else {
            return null;
        }
    }
    public ResponseEntity<AuthResponse> getOneUsersByMail(Profil profil){
        AuthResponse authResponse = new AuthResponse();

        if(getProfilByMail(profil.getMail()) != null) {
            authResponse.setMessage("Username already in use.");
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        } else {
            accountService.addProfil(profil);
            return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
        }
    }

    public Profil getProfilByMail(String mail){
        return profilRepository.findByMail(mail);
    }

    public Profil getOneUserByUserName(String name){
        return profilRepository.findByName(name);
    }

    public Profil saveOneUser(Profil newProfil){
        return profilRepository.save(newProfil);
    }
}
