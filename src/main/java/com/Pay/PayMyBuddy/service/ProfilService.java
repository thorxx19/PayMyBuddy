package com.Pay.PayMyBuddy.service;


import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.repository.ProfilRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
public class ProfilService {

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
}
