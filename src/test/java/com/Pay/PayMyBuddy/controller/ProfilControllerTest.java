package com.Pay.PayMyBuddy.controller;


import com.Pay.PayMyBuddy.repository.AccountRepository;
import com.Pay.PayMyBuddy.repository.ProfilRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

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
    ProfilRepository profilRepository;
    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void setup(){
        profilRepository.deleteAll();
        accountRepository.deleteAll();
    }


    @Test
    @DisplayName("test le end point GET/clientId")
    void getCliendById(){

        try {
            mockMvc.perform(get("/clientId").param("id","1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.name").value("Olivier"));
        } catch (Exception e) {
            log.error("error :", e);
        }

    }
    @Test
    @DisplayName("test le end point GET/clients")
    void getAllClients(){

        try {
            mockMvc.perform(get("/clients"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$[0].name").value("Olivier"));
        } catch (Exception e) {
            log.error("error :", e);
        }

    }

}
