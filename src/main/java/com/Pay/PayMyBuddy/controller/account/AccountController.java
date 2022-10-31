package com.Pay.PayMyBuddy.controller.account;


import com.Pay.PayMyBuddy.model.*;
import com.Pay.PayMyBuddy.repository.ConnectRepository;
import com.Pay.PayMyBuddy.repository.ProfilRepository;
import com.Pay.PayMyBuddy.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@CrossOrigin("http://localhost:3000/")
public class AccountController {

    @Autowired
    private ProfilRepository profilRepository;
    @Autowired
    private ConnectRepository connectRepository;
    @Autowired
    private AccountService accountService;

    /**
     * methode pour récupérer les profils pas connecter avec le profil.id
     * @param id du profil
     * @return une liste de profil
     */
    @GetMapping("/mail")
    public List<Profil> getConnect(@RequestParam Long id){
        List<Profil> profilList = profilRepository.findByIdNot(id);
        List<Connect> connectList = connectRepository.findByIdUn_Id(id);
        List<Profil> listUnique = new ArrayList<>();
        List<Long> uniqueLongConnect = new ArrayList<>();
        List<Long> uniqueLongProfil = new ArrayList<>();

        for (Connect connect: connectList) {
            uniqueLongConnect.add(connect.getIdDeux().getId());
        }
        for (Profil profil: profilList) {
            uniqueLongProfil.add(profil.getId());
        }
        for (Long longInt:uniqueLongProfil) {
            if (!uniqueLongConnect.contains(longInt)){
                listUnique.add(profilRepository.findUniqueProfil(longInt));
            }
        }
        log.info(listUnique.toString());
        return listUnique;
    }

    /**
     * methode pour modifier le solde d'un profil
     * @param accountRequest la balance et l'id du profil
     * @return 200 ou 403
     */
    @PutMapping("/solde")
    public ResponseEntity<AuthResponse> modifSolde(@RequestBody AccountRequest accountRequest){
        return accountService.modifSolde(accountRequest.getBalance(), accountRequest.getId());
    }

}
