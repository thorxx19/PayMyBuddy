package com.Pay.PayMyBuddy.controller;


import com.Pay.PayMyBuddy.controller.login.AuthController;
import com.Pay.PayMyBuddy.model.AuthResponse;
import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.model.UserRequest;
import com.Pay.PayMyBuddy.repository.AccountRepository;
import com.Pay.PayMyBuddy.repository.ProfilRepository;
import com.Pay.PayMyBuddy.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author froid
 */

@AutoConfigureMockMvc
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class ProfilControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProfilRepository profilRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthController authController;


    @BeforeEach
    void setup(){
        Profil profil = new Profil();
        profil.setName("Olivier");
        profil.setLastName("FROIDEFOND");
        profil.setMail("olivier.test@net.fr");
        profil.setPassword("test@O.fr");

        accountService.addProfil(profil);
    }


    @Test
    @DisplayName("test le end point GET/client")
    void getCliendById(){

        UserRequest user = new UserRequest();
        user.setMail("olivier.test@net.fr");
        user.setPassword("test@O.fr");

        ResponseEntity<AuthResponse> response = authController.login(user);
        String bearer = response.getBody().getAccessToken();
        UUID id = response.getBody().getUserId();

        try {
            mockMvc.perform(get("/client").header(HttpHeaders.AUTHORIZATION,bearer)
                            .param("id", String.valueOf(id)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$[0].name").value("Olivier"));
        } catch (Exception e) {
            log.error("error :", e);
        }

    }
    @Test
    @DisplayName("test le end point GET/clients")
    void getAllClients(){

        UserRequest user = new UserRequest();
        user.setMail("olivier.test@net.fr");
        user.setPassword("test@O.fr");

        ResponseEntity<AuthResponse> response = authController.login(user);
        String bearer = response.getBody().getAccessToken();

        try {
            mockMvc.perform(get("/clients").header(HttpHeaders.AUTHORIZATION,bearer))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$[0].name").value("Olivier"));
        } catch (Exception e) {
            log.error("error :", e);
        }

    }
    @Test
    @DisplayName("tes le end point DELETE/client")
    void deleteClientsById(){
        UserRequest user = new UserRequest();
        user.setMail("olivier.test@net.fr");
        user.setPassword("test@O.fr");

        ResponseEntity<AuthResponse> response = authController.login(user);
        UUID id = response.getBody().getUserId();
        String bearer = response.getBody().getAccessToken();
        try {
            mockMvc.perform(delete("/client").header(HttpHeaders.AUTHORIZATION,bearer)
                            .param("id",String.valueOf(id)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }
    @AfterEach
    void setupEnd(){
        profilRepository.deleteAll();
    }

}
