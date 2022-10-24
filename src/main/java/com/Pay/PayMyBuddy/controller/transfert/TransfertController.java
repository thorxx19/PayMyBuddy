package com.Pay.PayMyBuddy.controller.transfert;


import com.Pay.PayMyBuddy.model.PostTransfert;
import com.Pay.PayMyBuddy.model.Transfer;
import com.Pay.PayMyBuddy.repository.TransferRepository;
import com.Pay.PayMyBuddy.service.TransfertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@Slf4j
@CrossOrigin("http://localhost:3000/")
@RequestMapping("/transfert")
public class TransfertController {

    @Autowired
    TransferRepository transferRepository;
    @Autowired
    TransfertService transfertService;


    @PostMapping
    public @ResponseBody boolean postTransfer(@RequestBody PostTransfert postTransfert){
        return transfertService.transfert(postTransfert);
    }

    @GetMapping
    public List<Transfer> getTransferById(@RequestParam long id){
        return transferRepository.findByIdDebtor_IdOrderByDateDesc(id);
    }

}
