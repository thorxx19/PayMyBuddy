package com.Pay.paymybuddy.controller;


import com.Pay.paymybuddy.model.*;
import com.Pay.paymybuddy.repository.ProfilRepository;
import com.Pay.paymybuddy.repository.TransferRepository;
import com.Pay.paymybuddy.service.AccountService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.UUID;


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
    private ProfilRepository profilRepository;
    @Autowired
    private TransferRepository transferRepository;


    @BeforeEach
    void setup() {
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

        userUn.setMail("froidefond.olivier@net.fr");
        userUn.setPassword("test@O.fr");

        userDeux.setMail("dupont.thierry@net.fr");
        userDeux.setPassword("test@T.fr");

        ResponseEntity<AuthResponse> responseUn = authController.login(userUn);
        ResponseEntity<AuthResponse> responseDeux = authController.login(userDeux);
        UUID idDeux = responseDeux.getBody().getUserId();
        String bearerUn = responseUn.getBody().getAccessToken();
        String bearer = responseUn.getBody().getAccessToken();



        try {
            mockMvc.perform(put("/solde").header(HttpHeaders.AUTHORIZATION,bearer)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"balance\": 100}"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(jsonPath("$.message").value("Modification sur votre compte effectif"));
        } catch (Exception e) {
            log.error("Error: ", e);
        }

        try {
            mockMvc.perform(post("/transfert").header(HttpHeaders.AUTHORIZATION,bearerUn)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"idCredit\" :\" " + idDeux + "\",\"balance\" : 5,\"descriptif\" : \"test\"}"))
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

        userUn.setMail("froidefond.olivier@net.fr");
        userUn.setPassword("test@O.fr");

        userDeux.setMail("dupont.thierry@net.fr");
        userDeux.setPassword("test@T.fr");

        ResponseEntity<AuthResponse> responseUn = authController.login(userUn);
        String bearerUn = responseUn.getBody().getAccessToken();


        accountService.modifSolde(BigDecimal.valueOf(100));
        accountService.modifSolde(BigDecimal.valueOf(100));

        try {
            mockMvc.perform(post("/transfert").header(HttpHeaders.AUTHORIZATION,bearerUn)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"idCredit\" :\"00000000-0000-0000-0000-000000000000\",\"balance\" : 5,\"descriptif\" : \"test\"}"))
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

        userUn.setMail("froidefond.olivier@net.fr");
        userUn.setPassword("test@O.fr");

        userDeux.setMail("dupont.thierry@net.fr");
        userDeux.setPassword("test@T.fr");

        ResponseEntity<AuthResponse> responseUn = authController.login(userUn);
        ResponseEntity<AuthResponse> responseDeux = authController.login(userDeux);
        UUID idDeux = responseDeux.getBody().getUserId();
        String bearerUn = responseUn.getBody().getAccessToken();

        try {
            mockMvc.perform(post("/transfert").header(HttpHeaders.AUTHORIZATION,bearerUn)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"idCredit\" :\" " + idDeux + "\",\"balance\" : 5,\"descriptif\" : \"test\"}"))
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

        userUn.setMail("froidefond.olivier@net.fr");
        userUn.setPassword("test@O.fr");

        userDeux.setMail("dupont.thierry@net.fr");
        userDeux.setPassword("test@T.fr");

        ResponseEntity<AuthResponse> responseUn = authController.login(userUn);
        ResponseEntity<AuthResponse> responseDeux = authController.login(userDeux);
        UUID idDeux = responseDeux.getBody().getUserId();
        String bearerUn = responseUn.getBody().getAccessToken();
        String bearerDeux = responseUn.getBody().getAccessToken();

        try {
            mockMvc.perform(put("/solde").header(HttpHeaders.AUTHORIZATION,bearerUn)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"balance\": 100}"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(jsonPath("$.message").value("Modification sur votre compte effectif"));
        } catch (Exception e) {
            log.error("Error: ", e);
        }
        try {
            mockMvc.perform(put("/solde").header(HttpHeaders.AUTHORIZATION,bearerDeux)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"balance\": 100}"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(jsonPath("$.message").value("Modification sur votre compte effectif"));
        } catch (Exception e) {
            log.error("Error: ", e);
        }
        try {
            mockMvc.perform(post("/transfert").header(HttpHeaders.AUTHORIZATION,bearerUn)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"idCredit\" :\" " + idDeux + "\",\"balance\" : 5,\"descriptif\" : \"Un repas\"}"))
                    .andExpect(content().contentType("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("Transfert réussie"));
        } catch (Exception e) {
            log.error("Error: ", e);
        }
        try {
            mockMvc.perform(post("/transfert").header(HttpHeaders.AUTHORIZATION,bearerUn)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"idCredit\" :\" " + idDeux + "\",\"balance\" : 10,\"descriptif\" : \"Un jeux\"}"))
                    .andExpect(content().contentType("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("Transfert réussie"));
        } catch (Exception e) {
            log.error("Error: ", e);
        }
        try {
            mockMvc.perform(get("/transferts").header(HttpHeaders.AUTHORIZATION,bearerUn))
                    .andExpect(content().contentType("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.datas[0].description").value("Un repas"))
                    .andExpect(jsonPath("$.datas[0].amount").value("5.0000€"))
                    .andExpect(jsonPath("$.datas[1].description").value("Un jeux"))
                    .andExpect(jsonPath("$.datas[1].amount").value("10.0000€"));
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }
    @Test@DisplayName("test le end point GET /transfert")
    void getTransfert(){
        UserRequest userUn = new UserRequest();
        UserRequest userDeux = new UserRequest();

        userUn.setMail("froidefond.olivier@net.fr");
        userUn.setPassword("test@O.fr");

        userDeux.setMail("dupont.thierry@net.fr");
        userDeux.setPassword("test@T.fr");

        ResponseEntity<AuthResponse> responseUn = authController.login(userUn);
        ResponseEntity<AuthResponse> responseDeux = authController.login(userDeux);
        UUID idDeux = responseDeux.getBody().getUserId();
        String bearerUn = responseUn.getBody().getAccessToken();
        String bearerDeux = responseDeux.getBody().getAccessToken();


        try {
            mockMvc.perform(put("/solde").header(HttpHeaders.AUTHORIZATION,bearerUn)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"balance\": 50}"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(jsonPath("$.message").value("Modification sur votre compte effectif"));
        } catch (Exception e) {
            log.error("Error: ", e);
        }
        try {
            mockMvc.perform(put("/solde").header(HttpHeaders.AUTHORIZATION,bearerDeux)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"balance\": 50}"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(jsonPath("$.message").value("Modification sur votre compte effectif"));
        } catch (Exception e) {
            log.error("Error: ", e);
        }
        try {
            mockMvc.perform(post("/transfert").header(HttpHeaders.AUTHORIZATION,bearerUn)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"idCredit\" :\" " + idDeux + "\",\"balance\" : 5,\"descriptif\" : \"Un repas\"}"))
                    .andExpect(content().contentType("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("Transfert réussie"));
        } catch (Exception e) {
            log.error("Error: ", e);
        }
        try {
            mockMvc.perform(post("/transfert").header(HttpHeaders.AUTHORIZATION,bearerUn)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"idCredit\" :\" " + idDeux + "\",\"balance\" : 10,\"descriptif\" : \"Un jeux\"}"))
                    .andExpect(content().contentType("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("Transfert réussie"));
        } catch (Exception e) {
            log.error("Error: ", e);
        }

        try {
            mockMvc.perform(get("/transferts").header(HttpHeaders.AUTHORIZATION,bearerUn))
                    .andExpect(content().contentType("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.datas[0].description").value("Un repas"))
                    .andExpect(jsonPath("$.datas[0].amount").value("5.0000€"));
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
