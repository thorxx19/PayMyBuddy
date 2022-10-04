package com.Pay.PayMyBuddy.controller.connect;

import com.Pay.PayMyBuddy.model.Connect;
import com.Pay.PayMyBuddy.repository.ConnectRepository;
import com.Pay.PayMyBuddy.service.ProfilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin("http://localhost:3000/")
public class ConnectController {

    @Autowired
    ConnectRepository connectRepository;
    @Autowired
    ProfilService profilService;


    @GetMapping("/connect")
    public List<Connect> getConnect(){
        return connectRepository.findAll();
    }
    @PostMapping("/connect")
    public String postConnect(@RequestParam long idUn, long idDeux){
        Connect connect = new Connect();

        connect.setIdUn(profilService.getProfil(idUn));
        connect.setIdDeux(profilService.getProfil(idDeux));

        connectRepository.save(connect);
        return "save";
    }

    @GetMapping("/connectId")
    public Iterable<Connect> getConnectById(@RequestParam long idUn) {
        return connectRepository.findByIdAll(idUn);
    }

}
