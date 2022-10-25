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
    private ProfilRepository profilRepository;


    @GetMapping("/clients")
    public List<Profil> getClient(){
        return profilRepository.findAll();
    }
    @GetMapping("/client")
    public List<Profil> getClientById(@RequestParam long id){
        return profilRepository.findByIdList(id);
    }
    @DeleteMapping("/client")
    public String deleteClient(@RequestParam long id) throws ServiceException {
        profilRepository.deleteById(id);
        return "Profil delete";
    }


}
