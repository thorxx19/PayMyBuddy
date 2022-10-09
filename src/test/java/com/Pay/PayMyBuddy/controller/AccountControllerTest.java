package com.Pay.PayMyBuddy.controller;



import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.repository.AccountRepository;
import com.Pay.PayMyBuddy.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author froid
 */

@AutoConfigureMockMvc
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void setup(){


        Profil profil = new Profil();
        profil.setName("Renée");
        profil.setLastName("LAPLACE");
        profil.setMail("test@test.fr");
        profil.setPassword("test@O.fr");

        accountService.addProfil(profil);
    }



    @Test
    @DisplayName("test le end point /add")
    void addAccount(){

        try {
            mockMvc.perform(post("/add").contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\" : \"Olivier\",\"lastName\" : \"FROIDEFOND\",\"mail\" : \"test@test.fr\",\"password\" : \"admin@O.test\"}"))
                            .andExpect(status().is2xxSuccessful());;

        } catch (Exception e) {
            log.error("Error : ", e);
        }
    }


}
