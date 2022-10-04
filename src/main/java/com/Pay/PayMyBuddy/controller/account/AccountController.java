package com.Pay.PayMyBuddy.controller.account;


import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.repository.ClientRepository;
import com.Pay.PayMyBuddy.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin("http://localhost:3000/")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/add")
    public @ResponseBody String addProfil(@RequestParam String name, String lastName, String mail, String password){
        return accountService.addProfil(name,lastName,mail,password);
    }




}
