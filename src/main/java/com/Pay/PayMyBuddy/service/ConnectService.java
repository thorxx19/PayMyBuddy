package com.Pay.PayMyBuddy.service;



import com.Pay.PayMyBuddy.jwt.JwtUserDetails;
import com.Pay.PayMyBuddy.model.AuthResponse;
import com.Pay.PayMyBuddy.model.Connect;
import com.Pay.PayMyBuddy.repository.ConnectRepository;
import com.Pay.PayMyBuddy.repository.ProfilRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

    /**
     * methode pour connecter 2 profil entre eux pour échanger de l'argent
     * profilid l'id du débiteur
     * @param idDeux l'ide du créditeur
     * @return 202 ou 400
     * @throws ServiceException exception
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<AuthResponse> postConnect(long idDeux) throws ServiceException {

        JwtUserDetails profil = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long profilId = profil.getId();

        Connect connect = new Connect();
        AuthResponse authResponse = new AuthResponse();


        if (!profilRepository.existsById(profilId) || !profilRepository.existsById(idDeux)){
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
                authResponse.setData(profilRepository.findById(idDeux));
                connectRepository.save(connect);
                return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
            }
        }
    }
    //todo ajouter javadoc
    public ResponseEntity<AuthResponse> getConnectById(){

        AuthResponse authResponse = new AuthResponse();

        JwtUserDetails profil = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long profilId = profil.getId();
        authResponse.setDatas(connectRepository.findByIdUn_Id(profilId));

        return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }

}
