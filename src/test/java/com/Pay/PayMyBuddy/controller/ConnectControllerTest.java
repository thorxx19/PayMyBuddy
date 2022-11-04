package com.Pay.PayMyBuddy.controller;


import com.Pay.PayMyBuddy.controller.login.AuthController;
import com.Pay.PayMyBuddy.model.AuthResponse;
import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.model.UserRequest;
import com.Pay.PayMyBuddy.repository.ConnectRepository;
import com.Pay.PayMyBuddy.repository.ProfilRepository;
import com.Pay.PayMyBuddy.service.AccountService;
import com.Pay.PayMyBuddy.service.ConnectService;
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

@AutoConfigureMockMvc
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class ConnectControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthController authController;
    @Autowired
    private ProfilRepository profilRepository;
    @Autowired
    private ConnectRepository connectRepository;
    @Autowired
    private ConnectService connectService;

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
    @DisplayName("test le end point POST/connect")
    void postConnect(){

        UserRequest userUn = new UserRequest();
        UserRequest userDeux = new UserRequest();

        userUn.setMail("froidefond.olivier@net.fr");
        userUn.setPassword("test@O.fr");

        userDeux.setMail("dupont.thierry@net.fr");
        userDeux.setPassword("test@T.fr");

        ResponseEntity<AuthResponse> responseUn = authController.login(userUn);
        ResponseEntity<AuthResponse> responseDeux = authController.login(userDeux);
        Long idUn = responseUn.getBody().getUserId();
        String bearer = responseUn.getBody().getAccessToken();
        Long idDeux = responseDeux.getBody().getUserId();

        try {
            mockMvc.perform(post("/connect").contentType(MediaType.APPLICATION_JSON)
                            .content("{\"idUn\" :" + idUn + ",\"idDeux\" : " + idDeux + " }")
                    .header(HttpHeaders.AUTHORIZATION,bearer)).andExpect(status().is2xxSuccessful());
        } catch (Exception e) {
            log.error("Error: ", e);

        }
    }
    @Test
    @DisplayName("test le end point POST/connect connection déja existant")
    void postConnectExistant(){

        UserRequest userUn = new UserRequest();
        UserRequest userDeux = new UserRequest();

        userUn.setMail("froidefond.olivier@net.fr");
        userUn.setPassword("test@O.fr");

        userDeux.setMail("dupont.thierry@net.fr");
        userDeux.setPassword("test@T.fr");

        ResponseEntity<AuthResponse> responseUn = authController.login(userUn);
        ResponseEntity<AuthResponse> responseDeux = authController.login(userDeux);
        Long idUn = responseUn.getBody().getUserId();
        String bearer = responseUn.getBody().getAccessToken();
        Long idDeux = responseDeux.getBody().getUserId();

        try {
            mockMvc.perform(post("/connect").contentType(MediaType.APPLICATION_JSON)
                    .content("{\"idDeux\" : " + idDeux + " }")
                    .header(HttpHeaders.AUTHORIZATION,bearer)).andExpect(status().is2xxSuccessful());
        } catch (Exception e) {
            log.error("Error: ", e);

        }

        try {
            mockMvc.perform(post("/connect").contentType(MediaType.APPLICATION_JSON)
                    .content("{\"idUn\" :" + idUn + ",\"idDeux\" : " + idDeux + " }")
                    .header(HttpHeaders.AUTHORIZATION,bearer)).andExpect(status().is4xxClientError());
        } catch (Exception e) {
            log.error("Error: ", e);

        }
    }
    @Test
    @DisplayName("test le end point POST/connect avec un profil qui n'éxiste pas")
    void postConnectError(){

        UserRequest userUn = new UserRequest();

        userUn.setMail("froidefond.olivier@net.fr");
        userUn.setPassword("test@O.fr");

        ResponseEntity<AuthResponse> responseUn = authController.login(userUn);
        String bearer = responseUn.getBody().getAccessToken();


        try {
            mockMvc.perform(post("/connect").contentType(MediaType.APPLICATION_JSON)
                    .content("{\"idDeux\" : 500000 }")
                    .header(HttpHeaders.AUTHORIZATION,bearer)).andExpect(status().is4xxClientError());
        } catch (Exception e) {
            log.error("Error: ", e);

        }
    }
    @Test
    @DisplayName("test le end point GET/connect")
    void getConnect(){

        UserRequest userUn = new UserRequest();
        UserRequest userDeux = new UserRequest();

        userUn.setMail("froidefond.olivier@net.fr");
        userUn.setPassword("test@O.fr");

        userDeux.setMail("dupont.thierry@net.fr");
        userDeux.setPassword("test@T.fr");

        ResponseEntity<AuthResponse> responseUn = authController.login(userUn);
        ResponseEntity<AuthResponse> responseDeux = authController.login(userDeux);
        String bearer = responseUn.getBody().getAccessToken();
        Long idDeux = responseDeux.getBody().getUserId();

        try {
            mockMvc.perform(post("/connect").contentType(MediaType.APPLICATION_JSON)
                    .content("{\"idDeux\" : " + idDeux + " }")
                    .header(HttpHeaders.AUTHORIZATION,bearer)).andExpect(status().is2xxSuccessful());
        } catch (Exception e) {
            log.error("Error: ", e);

        }

        try {
            mockMvc.perform(get("/connect").header(HttpHeaders.AUTHORIZATION,bearer))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.datas[0].idUn.name").value("Olivier"))
                    .andExpect(jsonPath("$.datas[0].idDeux.name").value("Thierry"));
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }
    @Test
    @DisplayName("test le end point GET/mail")
    void getMail(){

        UserRequest user = new UserRequest();
        user.setMail("froidefond.olivier@net.fr");
        user.setPassword("test@O.fr");

        ResponseEntity<AuthResponse> response = authController.login(user);
        String bearer = response.getBody().getAccessToken();
        Long id = response.getBody().getUserId();

        try {
            mockMvc.perform(get("/mail").header(HttpHeaders.AUTHORIZATION,bearer)
                    .param("id", String.valueOf(id)))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$[0].name").value("Thierry"));
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }
    @Test
    @DisplayName("test le end point put/solde avec resulte 200")
    void putSolde(){

        UserRequest user = new UserRequest();
        user.setMail("froidefond.olivier@net.fr");
        user.setPassword("test@O.fr");

        ResponseEntity<AuthResponse> response = authController.login(user);
        String bearer = response.getBody().getAccessToken();

        try {
            mockMvc.perform(put("/solde").header(HttpHeaders.AUTHORIZATION,bearer)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"balance\": 100}"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(jsonPath("$.message").value("Modification sur votre compte effectif"));
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }
    @Test
    @DisplayName("test le end point put/solde avec resulte 400")
    void putSoldeError(){

        UserRequest user = new UserRequest();
        user.setMail("froidefond.olivier@net.fr");
        user.setPassword("test@O.fr");

        ResponseEntity<AuthResponse> response = authController.login(user);
        String bearer = response.getBody().getAccessToken();
        Long id = response.getBody().getUserId();

        try {
            mockMvc.perform(put("/solde").header(HttpHeaders.AUTHORIZATION,bearer)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"id\":\" " + id + "\",\"balance\": -100}"))
                    .andExpect(status().is4xxClientError())
                    .andExpect(jsonPath("$.message").value("Modification sur votre compte impossible"));
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }

    @AfterEach
    void setupEnd(){
        connectRepository.deleteAll();
        profilRepository.deleteAll();
    }

}
