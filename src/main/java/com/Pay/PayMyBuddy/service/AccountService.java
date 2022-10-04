package com.Pay.PayMyBuddy.service;

import com.Pay.PayMyBuddy.model.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class AccountService {

    public Account addNewAccount(){
        Account account = new Account();
        BigDecimal i = new BigDecimal(100);
        account.setBalance(i);
        Date date = new Date();
        account.setDate(date);
        return account;
    }

}
