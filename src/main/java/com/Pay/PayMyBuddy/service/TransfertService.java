package com.Pay.PayMyBuddy.service;

import com.Pay.PayMyBuddy.jwt.JwtUserDetails;
import com.Pay.PayMyBuddy.model.*;
import com.Pay.PayMyBuddy.repository.ProfilRepository;
import com.Pay.PayMyBuddy.repository.TransferRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


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

        JwtUserDetails profilRecup = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID profilId = profilRecup.getId();


        Date date = new Date();
        if (profilRepository.existsById(postTransfert.getIdCredit()) && profilRepository.existsByIdEquals(profilId)) {
            transfer.setDate(date);
            transfer.setDescription(postTransfert.getDescriptif());
            Profil profilDebtor = profilService.getProfilDebtor(profilId, postTransfert.getBalance());
            if (profilDebtor != null){
                transfer.setIdDebtor(profilDebtor);
            } else {
                authResponse.setMessage("Transfert pas réussie");
                return new ResponseEntity<>(authResponse,HttpStatus.BAD_REQUEST);
            }
            authResponse.setMessage("Transfert réussie");
            transfer.setIdCredit(profilService.getProfilCredit(postTransfert.getIdCredit(), postTransfert.getBalance()));
            transfer.setAmount(postTransfert.getBalance());
            transferRepository.save(transfer);
            return new ResponseEntity<>(authResponse,HttpStatus.OK);
        } else {
            authResponse.setMessage("Transfert pas réussie");
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }
    }
    //todo ajouter javadoc
    public ResponseEntity<AuthResponse> getTransfertById(){

        AuthResponse authResponse = new AuthResponse();

        List<TransfertDto> transfertDtoList = new ArrayList<>();

        JwtUserDetails profilRecup = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID profilId = profilRecup.getId();

        List<Transfer> transfersList = transferRepository.findByIdDebtor_IdOrderByDateDesc(profilId);

        for (Transfer transfer : transfersList) {
            TransfertDto transfertDto = new TransfertDto();

            transfertDto.setId(transfer.getId());
            transfertDto.setDescription(transfer.getDescription());
            transfertDto.setAmount(transfer.getAmount().toString()+"€");
            transfertDto.setIdDebtor(transfer.getIdDebtor());
            transfertDto.setIdCredit(transfer.getIdCredit());

            transfertDtoList.add(transfertDto);
        }

        authResponse.setDatas(transfertDtoList);

        return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }
    //todo ajouter javadoc
    public List<Transfer> getFirstTrasnfert(){

        JwtUserDetails profilRecup = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID profilId = profilRecup.getId();

        return transferRepository.findFirstByIdDebtor_IdOrderByDateDesc(profilId);
    }

}
