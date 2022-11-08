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

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin("http://localhost:3000/")
public class ConnectController {

    @Autowired
    private ConnectRepository connectRepository;
    @Autowired
    private ConnectService connectService;

    /**
     * methode pour connecter 2 profil
     *
     * @param connectDto un object avec l'id du débiteur et du créditeur
     * @return 202 ou 400
     */
    @PostMapping("/connect")
    public ResponseEntity<AuthResponse> postConnect(@Valid @RequestBody ConnectDto connectDto){
        return connectService.postConnect(connectDto.getIdDeux());
    }

    /**
     * methode pour récup les connection pour un profil donné
     *
     * @return une liste de profil connecter
     */
    @GetMapping("/connect")
    public ResponseEntity<AuthResponse> getConnectById() {
        return connectService.getConnectById();
    }

}
