package com.Pay.PayMyBuddy.service;

import com.Pay.PayMyBuddy.model.Account;
import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

@Service
public class AccountService {

    @Autowired
    ClientRepository clientRepository;


    public Account addNewAccount(){
        Account account = new Account();
        BigDecimal i = new BigDecimal(100);
        account.setBalance(i);
        Date date = new Date();
        account.setDate(date);
        return account;
    }
    @Transactional(rollbackFor = { SQLException.class })
    public String addProfil(String name, String lastName, String mail, String password){


        Profil profil = new Profil();

        profil.setName(name);
        profil.setLastName(lastName);
        profil.setMail(mail);
        profil.setPassword(password);
        profil.setAccountId(addNewAccount());

        clientRepository.save(profil);
        try {
            throw new SQLException("Throwing exception for demoing rollback");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
