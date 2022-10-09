package com.Pay.PayMyBuddy.service;

import com.Pay.PayMyBuddy.model.Account;
import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.repository.AccountRepository;
import com.Pay.PayMyBuddy.repository.ProfilRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class AccountService {

    @Autowired
    ProfilRepository profilRepository;
    @Autowired
    AccountRepository accountRepository;

    @Transactional(rollbackFor = Exception.class)
    public String addProfil(Profil profilAdd) throws ServiceException {

        Profil profil = new Profil();

        if (profilRepository.existsByMail(profilAdd.getMail())){
            return "profil déja existant";
        } else {
            profil.setName(profilAdd.getName());
            profil.setLastName(profilAdd.getLastName());
            profil.setMail(profilAdd.getMail());
            profil.setPassword(profilAdd.getPassword());
            profil.setAccountId(addNewAccount());
            profilRepository.save(profil);

            return "save";
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
