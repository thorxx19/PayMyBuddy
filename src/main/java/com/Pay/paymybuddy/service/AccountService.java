package com.Pay.paymybuddy.service;

import com.Pay.paymybuddy.model.JwtUserDetails;
import com.Pay.paymybuddy.model.Account;
import com.Pay.paymybuddy.model.AuthResponse;
import com.Pay.paymybuddy.model.Connect;
import com.Pay.paymybuddy.model.Profil;
import com.Pay.paymybuddy.repository.ProfilRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AccountService {

    @Autowired
    private ProfilRepository profilRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ConnectService connectService;

    /**
     * methode pour creer un nouveau profil
     *
     * @param profilAdd le profila add
     * @return 201
     */
    @Transactional
    public ResponseEntity<AuthResponse> addProfil(Profil profilAdd){

        Profil profil = new Profil();
        AuthResponse authResponse = new AuthResponse();

        try {
            profil.setName(profilAdd.getName());
            profil.setLastName(profilAdd.getLastName());
            profil.setMail(profilAdd.getMail());
            profil.setPassword(passwordEncoder.encode(profilAdd.getPassword()));
            profil.setAccountId(addNewAccount());
            Profil response = profilRepository.save(profil);
            authResponse.setMessage("User successfully registered.");
            authResponse.setUserId(response.getId());
            return new ResponseEntity<>(authResponse, HttpStatus.CREATED);

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }



    }

    /**
     * methode pour modifier le solde d'un profil
     *
     * @param balance l'argent a créditer ou débiter
     * @return 200 ou 403
     */
    public ResponseEntity<AuthResponse> modifSolde(BigDecimal balance){

        JwtUserDetails profil = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID profilId = profil.getId();
        AuthResponse authResponse = new AuthResponse();

        Profil profilUpdate = profilRepository.findUniqueProfil(profilId);

        BigDecimal solde = profilUpdate.getAccountId().getBalance();
        BigDecimal newSolde = solde.add(balance);

        if (newSolde.intValue() >= 0) {
            authResponse.setMessage("Modification sur votre compte effectif");
            profilUpdate.getAccountId().setBalance(newSolde);
            profilRepository.save(profilUpdate);
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } else {
            authResponse.setMessage("Modification sur votre compte impossible");
            return new ResponseEntity<>(authResponse, HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Methode qui gére la connection entre profil
     *
     * @return une list de profil non connecter
     */
    public List<Profil> getConnect() {

        JwtUserDetails profilRecup = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID profilId = profilRecup.getId();

        List<Profil> profilList = profilRepository.findByIdNot(profilId);
        List<Connect> connectList = connectService.findByIdUn(profilId);
        List<Profil> listUnique = new ArrayList<>();
        List<UUID> uniqueLongConnect = new ArrayList<>();
        List<UUID> uniqueLongProfil = new ArrayList<>();

        for (Connect connect: connectList) {
            uniqueLongConnect.add(connect.getIdDeux().getId());
        }
        for (Profil profil: profilList) {
            uniqueLongProfil.add(profil.getId());
        }
        for (UUID longInt:uniqueLongProfil) {
            if (!uniqueLongConnect.contains(longInt)){
                listUnique.add(profilRepository.findUniqueProfil(longInt));
            }
        }
        return listUnique;
    }

    /**
     * methode pour creer un compte bancaire au nouveau client
     *
     * @return un compte bancaire
     */
    private Account addNewAccount(){
        Account account = new Account();
        BigDecimal i = new BigDecimal(0);
        account.setBalance(i);
        Date date = new Date();
        account.setDate(date);
        return account;
    }

}
