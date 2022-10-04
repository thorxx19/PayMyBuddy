package com.Pay.PayMyBuddy.service;


import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class ProfilService {

    @Autowired
    ClientRepository clientRepository;

    public Profil getProfilDebtor(Long id, BigDecimal balance){
        Profil profil = clientRepository.findByIdTest(id);
        BigDecimal solde = new BigDecimal(String.valueOf(profil.getAccountId().getBalance())) ;
        profil.getAccountId().setBalance(solde.subtract(balance));
        clientRepository.save(profil);
        return profil;
    }

    public Profil getProfilCredit(Long id, BigDecimal balance){
        Profil profil = clientRepository.findByIdTest(id);
        BigDecimal solde = new BigDecimal(String.valueOf(profil.getAccountId().getBalance())) ;
        profil.getAccountId().setBalance(solde.add(balance));
        clientRepository.save(profil);
        return profil;
    }
    public Profil getProfil(Long id){
        return clientRepository.findByIdTest(id);
    }

}
