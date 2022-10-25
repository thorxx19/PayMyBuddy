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
public class TransfertController {

    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private TransfertService transfertService;


    @PostMapping("/transfert")
    public @ResponseBody boolean postTransfert(@RequestBody PostTransfert postTransfert){
        return transfertService.transfert(postTransfert);
    }

    @GetMapping("/transferts")
    public List<Transfer> getTransfertById(@RequestParam long id){
        return transferRepository.findByIdDebtor_IdOrderByDateDesc(id);
    }
    @GetMapping("/transfert")
    public List<Transfer> getFirstTrasnfert(@RequestParam long id){
        return transferRepository.findFirstByIdDebtor_IdOrderByDateDesc(id);
    }

}
