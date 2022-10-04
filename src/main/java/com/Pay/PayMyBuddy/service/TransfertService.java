package com.Pay.PayMyBuddy.service;

import com.Pay.PayMyBuddy.model.PostTransfert;
import com.Pay.PayMyBuddy.model.Transfer;
import com.Pay.PayMyBuddy.repository.TransferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@Slf4j
public class TransfertService {

    @Autowired
    ProfilService profilService;
    @Autowired
    TransferRepository transferRepository;

   public String transfert(PostTransfert postTransfert){
        Transfer transfer = new Transfer();

        Date date = new Date();
        transfer.setDate(date);
        transfer.setDescription(postTransfert.getDescriptif());
        transfer.setIdDebtor(profilService.getProfilDebtor(postTransfert.getIdDebtor(), postTransfert.getBalance()));
        transfer.setIdCredit(profilService.getProfilCredit(postTransfert.getIdCredit(), postTransfert.getBalance()));
        transfer.setAmount(postTransfert.getBalance());

        Transfer response = transferRepository.save(transfer);

        return "save";
    }


}
