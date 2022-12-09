package com.Pay.paymybuddy.service;


import com.Pay.paymybuddy.model.JwtUserDetails;
import com.Pay.paymybuddy.model.AuthResponse;
import com.Pay.paymybuddy.model.Connect;
import com.Pay.paymybuddy.repository.ConnectRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ConnectService {

    @Autowired
    private ConnectRepository connectRepository;
    @Autowired
    private ProfilService profilService;

    /**
     * methode pour connecter 2 profil entre eux pour échanger de l'argent
     * profilid l'id du débiteur
     *
     * @param idDeux l'ide du créditeur
     * @return 202 ou 400
     * @throws ServiceException exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<AuthResponse> postConnect(UUID idDeux) {

        JwtUserDetails profil = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID profilId = profil.getId();

        Connect connect = new Connect();
        AuthResponse authResponse = new AuthResponse();


        if (!profilService.existsByIdEquals(profilId) || !profilService.existsByIdEquals(idDeux)){
            authResponse.setMessage("Connection impossible");
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        } else {
            if (connectRepository.existsByIdUn_IdAndIdDeux_Id(profilId,idDeux)){
                authResponse.setMessage("Connection déja existante");
                return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
            } else {
                authResponse.setMessage("Connection save");
                connect.setIdUn(profilService.getProfil(profilId));
                connect.setIdDeux(profilService.getProfil(idDeux));
                authResponse.setData(profilService.findByProfilUuid(idDeux));
                connectRepository.save(connect);
                return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
            }
        }
    }

    /**
     * Method les connection pour un profil connecter
     *
     * @return une liste de connection
     */
    public ResponseEntity<AuthResponse> getConnectById() {

        AuthResponse authResponse = new AuthResponse();

        JwtUserDetails profil = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID profilId = profil.getId();
        authResponse.setDatas(connectRepository.findByIdUn_Id(profilId));

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    /**
     * methode pour récupérer les connection d'un profil
     * @param profilId l'id du profil connecter
     * @return une liste de connection
     */
    public List<Connect> findByIdUn(UUID profilId){
       return connectRepository.findByIdUn_Id(profilId);
    }

}
