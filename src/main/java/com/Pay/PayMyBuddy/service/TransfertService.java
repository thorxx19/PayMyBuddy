package com.Pay.PayMyBuddy.service;

import com.Pay.PayMyBuddy.model.AuthResponse;
import com.Pay.PayMyBuddy.model.PostTransfert;
import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.model.Transfer;
import com.Pay.PayMyBuddy.repository.ProfilRepository;
import com.Pay.PayMyBuddy.repository.TransferRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
@Slf4j
public class TransfertService {

    @Autowired
    private ProfilService profilService;
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private ProfilRepository profilRepository;

    /**
     * methode pour transferet de l'argent entre profil
     * @param postTransfert l'object
     * @return true ou false
     * @throws ServiceException exception
     */
    @Transactional(rollbackFor = Exception.class)
   public ResponseEntity<AuthResponse> transfert(PostTransfert postTransfert) throws ServiceException {
        Transfer transfer = new Transfer();
        AuthResponse authResponse = new AuthResponse();

        Date date = new Date();
        if (profilRepository.existsById(postTransfert.getIdCredit()) && profilRepository.existsById(postTransfert.getIdDebtor())) {
            transfer.setDate(date);
            transfer.setDescription(postTransfert.getDescriptif());
            Profil profilDebtor = profilService.getProfilDebtor(postTransfert.getIdDebtor(), postTransfert.getBalance());
            if (profilDebtor != null){
                transfer.setIdDebtor(profilDebtor);
            } else {
                return new ResponseEntity<>(authResponse,HttpStatus.BAD_REQUEST);
            }
            transfer.setIdCredit(profilService.getProfilCredit(postTransfert.getIdCredit(), postTransfert.getBalance()));
            transfer.setAmount(postTransfert.getBalance());
            transferRepository.save(transfer);
            return new ResponseEntity<>(authResponse,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }
    }


}
