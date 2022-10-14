package com.Pay.PayMyBuddy.controller.profil;


import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.repository.AccountRepository;
import com.Pay.PayMyBuddy.repository.ProfilRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@CrossOrigin("http://localhost:3000/")
public class ProfilController {

    @Autowired
    ProfilRepository profilRepository;
    @Autowired
    AccountRepository accountRepository;

    @Transactional(readOnly = true)
    @GetMapping("/clientId")
    public Optional<Profil> getClientById(@RequestParam Long id){
        return profilRepository.findById(id);
    }
    @Transactional(readOnly = true)
    @GetMapping("/clients")
    public List<Profil> getClient(){
        return profilRepository.findAll();
    }

    @GetMapping("/client2")
    public void getclient2(@RequestParam String mail, String password){

        List<Profil> profil = profilRepository.findByMail(mail);
        profil.stream().forEach(x -> {if (x.getPassword().equals(password)){
            log.info("Connect");
        } else {
            log.info("No Connect");
        }
        });
    }
    @DeleteMapping("/clients")
    public String deleteClient(@RequestParam long id) throws ServiceException {

            profilRepository.deleteById(id);
            return "Profil delete";

    }


}
