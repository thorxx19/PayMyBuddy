package com.Pay.PayMyBuddy.service;

import com.Pay.PayMyBuddy.jwt.JwtUserDetails;
import com.Pay.PayMyBuddy.model.Account;
import com.Pay.PayMyBuddy.model.AuthResponse;
import com.Pay.PayMyBuddy.model.Connect;
import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.repository.AccountRepository;
import com.Pay.PayMyBuddy.repository.ConnectRepository;
import com.Pay.PayMyBuddy.repository.ProfilRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;

@Service
@Slf4j
public class AccountService {

    @Autowired
    private ProfilRepository profilRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ConnectRepository connectRepository;

    /**
     * methode pour creer un nouveau profil
     * @param profilAdd le profila add
     * @return 201
     * @throws ServiceException exception
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<AuthResponse> addProfil(Profil profilAdd) throws ServiceException {

        Profil profil = new Profil();
        AuthResponse authResponse = new AuthResponse();

            profil.setName(profilAdd.getName());
            profil.setLastName(profilAdd.getLastName());
            profil.setMail(profilAdd.getMail());
            profil.setPassword(passwordEncoder.encode(profilAdd.getPassword()));
            profil.setAccountId(addNewAccount());
            Profil response = profilRepository.save(profil);

          authResponse.setMessage("User successfully registered.");
          authResponse.setUserId(response.getId());
          return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    /**
     * methode pour modifier le solde d'un profil
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

        if (newSolde.intValue() >= 0){
            authResponse.setMessage("Modification sur votre compte effectif");
            profilUpdate.getAccountId().setBalance(newSolde);
            profilRepository.save(profilUpdate);
            return new ResponseEntity<>(authResponse,HttpStatus.OK);
        } else {
            authResponse.setMessage("Modification sur votre compte impossible");
            return new ResponseEntity<>(authResponse,HttpStatus.FORBIDDEN);
        }
    }
    //todo ajouter javadoc
    public List<Profil> getConnect(){

        JwtUserDetails profilRecup = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID profilId = profilRecup.getId();

        List<Profil> profilList = profilRepository.findByIdNot(profilId);
        List<Connect> connectList = connectRepository.findByIdUn_Id(profilId);
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
