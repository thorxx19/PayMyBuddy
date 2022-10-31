package com.Pay.PayMyBuddy.controller;


import com.Pay.PayMyBuddy.controller.account.AccountController;
import com.Pay.PayMyBuddy.controller.login.AuthController;
import com.Pay.PayMyBuddy.model.*;
import com.Pay.PayMyBuddy.repository.ConnectRepository;
import com.Pay.PayMyBuddy.repository.ProfilRepository;
import com.Pay.PayMyBuddy.repository.TransferRepository;
import com.Pay.PayMyBuddy.service.AccountService;
import com.Pay.PayMyBuddy.service.ConnectService;
import com.Pay.PayMyBuddy.service.TransfertService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;


@AutoConfigureMockMvc
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class TransfertControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthController authController;
    @Autowired
    private TransfertService transfertService;
    @Autowired
    private ProfilRepository profilRepository;
    @Autowired
    private TransferRepository transferRepository;


    @BeforeEach
    void setup(){
        Profil profil = new Profil();
        Profil profilBis = new Profil();

        profil.setName("Olivier");
        profil.setLastName("FROIDEFOND");
        profil.setMail("froidefond.olivier@net.fr");
        profil.setPassword("test@O.fr");

        profilBis.setName("Thierry");
        profilBis.setLastName("DUPONT");
        profilBis.setMail("dupont.thierry@net.fr");
        profilBis.setPassword("test@T.fr");

        accountService.addProfil(profil);
        accountService.addProfil(profilBis);
    }

    @Test
    @DisplayName("test le end point POST/transfert")
    void postTransfert(){

        UserRequest userUn = new UserRequest();
        UserRequest userDeux = new UserRequest();

        userUn.setName("Olivier");
        userUn.setPassword("test@O.fr");

        userDeux.setName("Thierry");
        userDeux.setPassword("test@T.fr");

        AuthResponse responseUn = authController.login(userUn);
        AuthResponse responseDeux = authController.login(userDeux);
        Long idUn = responseUn.getUserId();
        Long idDeux = responseDeux.getUserId();
        String bearerUn = responseUn.getAccessToken();


        accountService.modifSolde(BigDecimal.valueOf(100),idUn);
        accountService.modifSolde(BigDecimal.valueOf(100),idDeux);

        try {
            mockMvc.perform(post("/transfert").header(HttpHeaders.AUTHORIZATION,bearerUn)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"idDebtor\" :" + idUn + ",\"idCredit\" : " + idDeux + ",\"balance\" : 5,\"descriptif\" : \"test\"}"))
                    .andExpect(content().contentType("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("Transfert réussie"));
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }
    @Test
    @DisplayName("test le end point POST/transfert avec des profil inconnue")
    void postTransfertErrorProfilInconnue(){

        UserRequest userUn = new UserRequest();
        UserRequest userDeux = new UserRequest();

        userUn.setName("Olivier");
        userUn.setPassword("test@O.fr");

        userDeux.setName("Thierry");
        userDeux.setPassword("test@T.fr");

        AuthResponse responseUn = authController.login(userUn);
        AuthResponse responseDeux = authController.login(userDeux);
        Long idUn = responseUn.getUserId();
        Long idDeux = responseDeux.getUserId();
        String bearerUn = responseUn.getAccessToken();


        accountService.modifSolde(BigDecimal.valueOf(100),idUn);
        accountService.modifSolde(BigDecimal.valueOf(100),idDeux);

        try {
            mockMvc.perform(post("/transfert").header(HttpHeaders.AUTHORIZATION,bearerUn)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"idDebtor\" : 1000000,\"idCredit\" :1000001,\"balance\" : 5,\"descriptif\" : \"test\"}"))
                    .andExpect(content().contentType("application/json"))
                    .andExpect(status().is4xxClientError());
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }
    @Test
    @DisplayName("test le end point POST/transfert avec une erreur")
    void postTransfertError(){

        UserRequest userUn = new UserRequest();
        UserRequest userDeux = new UserRequest();

        userUn.setName("Olivier");
        userUn.setPassword("test@O.fr");

        userDeux.setName("Thierry");
        userDeux.setPassword("test@T.fr");

        AuthResponse responseUn = authController.login(userUn);
        AuthResponse responseDeux = authController.login(userDeux);
        Long idUn = responseUn.getUserId();
        Long idDeux = responseDeux.getUserId();
        String bearerUn = responseUn.getAccessToken();

        try {
            mockMvc.perform(post("/transfert").header(HttpHeaders.AUTHORIZATION,bearerUn)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"idDebtor\" :" + idUn + ",\"idCredit\" : " + idDeux + ",\"balance\" : 5,\"descriptif\" : \"test\"}"))
                    .andExpect(content().contentType("application/json"))
                    .andExpect(status().is4xxClientError())
                    .andExpect(jsonPath("$.message").value("Transfert pas réussie"));
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }
    @Test@DisplayName("test le end point GET /transferts")
    void getTransferts(){
        UserRequest userUn = new UserRequest();
        UserRequest userDeux = new UserRequest();

        userUn.setName("Olivier");
        userUn.setPassword("test@O.fr");

        userDeux.setName("Thierry");
        userDeux.setPassword("test@T.fr");

        AuthResponse responseUn = authController.login(userUn);
        AuthResponse responseDeux = authController.login(userDeux);
        Long idUn = responseUn.getUserId();
        Long idDeux = responseDeux.getUserId();
        String bearerUn = responseUn.getAccessToken();

        accountService.modifSolde(BigDecimal.valueOf(50),idUn);
        accountService.modifSolde(BigDecimal.valueOf(50),idDeux);


        PostTransfert transfert = new PostTransfert();
        PostTransfert transfert1 = new PostTransfert();

        transfert.setBalance(BigDecimal.valueOf(5));
        transfert.setIdCredit(idDeux);
        transfert.setIdDebtor(idUn);
        transfert.setDescriptif("Un repas");

        transfert1.setBalance(BigDecimal.valueOf(10));
        transfert1.setIdCredit(idDeux);
        transfert1.setIdDebtor(idUn);
        transfert1.setDescriptif("Un jeux");

        transfertService.transfert(transfert);
        transfertService.transfert(transfert1);

        try {
            mockMvc.perform(get("/transferts").header(HttpHeaders.AUTHORIZATION,bearerUn)
                    .param("id", String.valueOf(idUn)))
                    .andExpect(content().contentType("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].description").value("Un repas"))
                    .andExpect(jsonPath("$[0].amount").value(5.0000))
                    .andExpect(jsonPath("$[1].description").value("Un jeux"))
                    .andExpect(jsonPath("$[1].amount").value(10.0000));
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }
    @Test@DisplayName("test le end point GET /transfert")
    void getTransfert(){
        UserRequest userUn = new UserRequest();
        UserRequest userDeux = new UserRequest();

        userUn.setName("Olivier");
        userUn.setPassword("test@O.fr");

        userDeux.setName("Thierry");
        userDeux.setPassword("test@T.fr");

        AuthResponse responseUn = authController.login(userUn);
        AuthResponse responseDeux = authController.login(userDeux);
        Long idUn = responseUn.getUserId();
        Long idDeux = responseDeux.getUserId();
        String bearerUn = responseUn.getAccessToken();

        accountService.modifSolde(BigDecimal.valueOf(50),idUn);
        accountService.modifSolde(BigDecimal.valueOf(50),idDeux);


        PostTransfert transfert = new PostTransfert();
        PostTransfert transfert1 = new PostTransfert();

        transfert.setBalance(BigDecimal.valueOf(5));
        transfert.setIdCredit(idDeux);
        transfert.setIdDebtor(idUn);
        transfert.setDescriptif("Un repas");

        transfert1.setBalance(BigDecimal.valueOf(10));
        transfert1.setIdCredit(idDeux);
        transfert1.setIdDebtor(idUn);
        transfert1.setDescriptif("Un jeux");

        transfertService.transfert(transfert);
        transfertService.transfert(transfert1);

        try {
            mockMvc.perform(get("/transferts").header(HttpHeaders.AUTHORIZATION,bearerUn)
                            .param("id", String.valueOf(idUn)))
                    .andExpect(content().contentType("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].description").value("Un repas"))
                    .andExpect(jsonPath("$[0].amount").value(5.0000));
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }

    @AfterEach
    void setupEnd(){
        transferRepository.deleteAll();
        profilRepository.deleteAll();
    }

}
