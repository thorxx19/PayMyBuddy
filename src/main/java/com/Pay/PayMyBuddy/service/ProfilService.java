package com.Pay.PayMyBuddy.service;



import com.Pay.PayMyBuddy.jwt.JwtUserDetails;
import com.Pay.PayMyBuddy.model.AuthResponse;
import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.repository.ProfilRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
@Slf4j
public class ProfilService {



    @Autowired
    private ProfilRepository profilRepository;

    /**
     * methode pour récup le profil du débiteur et faire la transaction
     * @param id du débiteur
     * @param balance l'argent a enlever a sont compte
     * @return profil
     */
    @Transactional(readOnly = true)
    public Profil getProfilDebtor(Long id, BigDecimal balance){
        Profil profil = profilRepository.findByProfilId(id);
        BigDecimal solde = new BigDecimal(String.valueOf(profil.getAccountId().getBalance()));
        BigDecimal costs = balance.multiply(BigDecimal.valueOf(0.005));
        BigDecimal balanceCosts = balance.add(costs);
        if (solde.intValue() >= balanceCosts.intValue()) {
            profil.getAccountId().setBalance(solde.subtract(balanceCosts));
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
     * methode récup un profil avec sont mail
     * @param mail mail du profil
     * @return le profil
     */
    public Profil getProfilByMail(String mail){
        return profilRepository.findByMail(mail);
    }

    /**
     * methode récup un profil avec son nom
     * @param mail du profil
     * @return le profil
     */
    public Profil getOneUserByUserName(String mail){
        return profilRepository.findByMail(mail);
    }
    //todo javadoc
    public ResponseEntity<AuthResponse> deleteClient(Long id){

        AuthResponse authResponse = new AuthResponse();
        profilRepository.deleteById(id);
        authResponse.setMessage("Profil bien delete");
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
    public List<Profil> getClientById(){

        JwtUserDetails profilRecup = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long profilId = profilRecup.getId();

        return profilRepository.findByIdList(profilId);
    }
    public List<Profil> getClient(){
        return profilRepository.findAll();
    }
    public ResponseEntity<AuthResponse> getclientById(Long id){
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Profil idCredit");
        authResponse.setData(profilRepository.findByProfilId(id));
        return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }
}
