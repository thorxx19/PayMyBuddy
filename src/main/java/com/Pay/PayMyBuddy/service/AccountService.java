package com.Pay.PayMyBuddy.service;

import com.Pay.PayMyBuddy.model.Account;
import com.Pay.PayMyBuddy.model.AuthResponse;
import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.repository.AccountRepository;
import com.Pay.PayMyBuddy.repository.ProfilRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class AccountService {

    @Autowired
    private ProfilRepository profilRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     *
     * @param profilAdd
     * @return
     * @throws ServiceException
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
    public ResponseEntity<AuthResponse> modifSolde(BigDecimal balance, Long id){

        AuthResponse authResponse = new AuthResponse();

        Profil profilUpdate = profilRepository.findUniqueProfil(id);

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

    private Account addNewAccount(){
        Account account = new Account();
        BigDecimal i = new BigDecimal(100);
        account.setBalance(i);
        Date date = new Date();
        account.setDate(date);
        return account;
    }

}
