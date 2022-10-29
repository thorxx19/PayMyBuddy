package com.Pay.PayMyBuddy.service;



import com.Pay.PayMyBuddy.model.AuthResponse;
import com.Pay.PayMyBuddy.model.Connect;
import com.Pay.PayMyBuddy.repository.ConnectRepository;
import com.Pay.PayMyBuddy.repository.ProfilRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConnectService {

    @Autowired
    private ConnectRepository connectRepository;
    @Autowired
    private ProfilRepository profilRepository;
    @Autowired
    private ProfilService profilService;

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<AuthResponse> postConnect(long idUn, long idDeux) throws ServiceException {

        Connect connect = new Connect();
        AuthResponse authResponse = new AuthResponse();


        if (!profilRepository.existsById(idUn) || !profilRepository.existsById(idDeux)){
            authResponse.setMessage("Connection impossible");
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        } else {
            if (connectRepository.existsByIdUn_IdAndIdDeux_Id(idUn,idDeux)){
                authResponse.setMessage("Connection déja existante");
                return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
            } else {
                authResponse.setMessage("Connection save");
                connect.setIdUn(profilService.getProfil(idUn));
                connect.setIdDeux(profilService.getProfil(idDeux));
                authResponse.setData(profilRepository.findById(idDeux));
                connectRepository.save(connect);
                return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
            }
        }
    }
}
