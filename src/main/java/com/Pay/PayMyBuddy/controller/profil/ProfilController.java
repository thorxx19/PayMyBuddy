package com.Pay.PayMyBuddy.controller.profil;


import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@CrossOrigin("http://localhost:3000/")
public class ProfilController {

    @Autowired
    ClientRepository clientRepository;


    @GetMapping("/clientId")
    public Optional<Profil> getClientById(@RequestParam Long id){
        return clientRepository.findById(id);
    }

    @GetMapping("/clients")
    public List<Profil> getClient(){
        return clientRepository.findAll();
    }

    @GetMapping("client2")
    public void getclient2(@RequestParam String name, String password){

        List<Profil> profil = clientRepository.findNotAll(name);
        profil.stream().forEach(x -> {if (x.getPassword().equals(password)){
            log.info("Connect");
        } else {
            log.info("No Connect");
        }
        });
    }

}
