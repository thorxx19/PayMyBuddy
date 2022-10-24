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
@RequestMapping("/clients")
public class ProfilController {

    @Autowired
    private ProfilRepository profilRepository;


    @GetMapping
    public List<Profil> getClient(){
        return profilRepository.findAll();
    }
    @GetMapping("/")
    public Optional<Profil> getClientById(@RequestParam Long id){
        return profilRepository.findById(id);
    }
    @DeleteMapping("/")
    public String deleteClient(@RequestParam long id) throws ServiceException {
        profilRepository.deleteById(id);
        return "Profil delete";
    }


}
