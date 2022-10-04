package com.Pay.PayMyBuddy.service;



import com.Pay.PayMyBuddy.model.Connect;
import com.Pay.PayMyBuddy.repository.ConnectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectService {

    @Autowired
    ConnectRepository connectRepository;
    @Autowired
    ProfilService profilService;


    public String postConnect(long idUn, long idDeux){

        Connect connect = new Connect();

        connect.setIdUn(profilService.getProfil(idUn));
        connect.setIdDeux(profilService.getProfil(idDeux));

        connectRepository.save(connect);

        return "save";
    }

}
