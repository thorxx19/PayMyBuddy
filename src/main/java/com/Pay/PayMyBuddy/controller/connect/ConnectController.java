package com.Pay.PayMyBuddy.controller.connect;

import com.Pay.PayMyBuddy.model.AuthResponse;
import com.Pay.PayMyBuddy.model.Connect;
import com.Pay.PayMyBuddy.model.ConnectDto;
import com.Pay.PayMyBuddy.repository.ConnectRepository;
import com.Pay.PayMyBuddy.service.ConnectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/connects")
    public List<Connect> getConnect(){
        return connectRepository.findAll();
    }
    @PostMapping("/connect")
    public ResponseEntity<AuthResponse> postConnect(@RequestBody ConnectDto connectDto){
        return connectService.postConnect(connectDto.getIdUn(),connectDto.getIdDeux());
    }

    @GetMapping("/connect")
    public Iterable<Connect> getConnectById(@RequestParam long id) {
        return connectRepository.findByIdUn_Id(id);
    }

}
