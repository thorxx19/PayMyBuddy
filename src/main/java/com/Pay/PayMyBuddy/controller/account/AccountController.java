package com.Pay.PayMyBuddy.controller.account;


import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@CrossOrigin("http://localhost:3000/")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/add")
    public @ResponseBody String addProfil(@Valid @RequestBody Profil profil){
        return accountService.addProfil(profil);
    }

}
