package com.Pay.PayMyBuddy.service;



import com.Pay.PayMyBuddy.model.Connect;
import com.Pay.PayMyBuddy.repository.ConnectRepository;
import com.Pay.PayMyBuddy.repository.ProfilRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConnectService {

    @Autowired
    ConnectRepository connectRepository;
    @Autowired
    ProfilRepository profilRepository;
    @Autowired
    ProfilService profilService;

    @Transactional(rollbackFor = Exception.class)
    public String postConnect(long idUn, long idDeux) throws ServiceException {

        Connect connect = new Connect();

        if (!profilRepository.existsById(idUn) || !profilRepository.existsById(idDeux)){
            return "Connection impossible";
        } else {
            if (!connectRepository.existsById(idUn,idDeux).isEmpty()){
                return "Connection déja existante";
            } else {
                connect.setIdUn(profilService.getProfil(idUn));
                connect.setIdDeux(profilService.getProfil(idDeux));
                connectRepository.save(connect);
                return "Connection save";
            }
        }
    }
}
