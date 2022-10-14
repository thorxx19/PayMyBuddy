package com.Pay.PayMyBuddy.controller.connect;

import com.Pay.PayMyBuddy.model.Connect;
import com.Pay.PayMyBuddy.repository.ConnectRepository;
import com.Pay.PayMyBuddy.service.ConnectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin("http://localhost:3000/")
public class ConnectController {

    @Autowired
    ConnectRepository connectRepository;
    @Autowired
    ConnectService connectService;

    @Transactional(readOnly = true)
    @GetMapping("/connect")
    public List<Connect> getConnect(){
        return connectRepository.findAll();
    }
    @PostMapping("/connect")
    public String postConnect(@RequestParam long idUn, long idDeux){
        return connectService.postConnect(idUn,idDeux);
    }
    @Transactional(readOnly = true)
    @GetMapping("/connectId")
    public Iterable<Connect> getConnectById(@RequestParam long idUn) {
        return connectRepository.findByIdUn_Id(idUn);
    }

}
