package com.Pay.PayMyBuddy.controller;



import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.repository.ProfilRepository;
import com.Pay.PayMyBuddy.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author froid
 */

@AutoConfigureMockMvc
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class AuthControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ProfilRepository profilRepository;

    @BeforeEach
    void setup(){
        Profil profil = new Profil();
        profil.setName("Olivier");
        profil.setLastName("FROIDEFOND");
        profil.setMail("test@test.fr");
        profil.setPassword("test2@O.fr");


        accountService.addProfil(profil);
    }

    @Test
    @DisplayName("test le end point POST/auth/register")
    void postRegister(){
        try {
            mockMvc.perform(post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\" : \"robert\",\"lastName\" : \"FERRAT\",\"mail\" : \"lange@test.fr\",\"password\" : \"test2@O.fr\"}"))
                    .andExpect(status().isCreated());
        } catch (Exception e) {
            log.error("Error :", e);
        }
    }
    @Test
    @DisplayName("test le end point POST/auth/register avec un profil déja existant")
    void postRegisterError(){
        try {
            mockMvc.perform(post("/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\" : \"Olivier\",\"lastName\" : \"FROIDEFOND\",\"mail\" : \"test@test.fr\",\"password\" : \"test2@O.fr\"}"))
                    .andExpect(status().is4xxClientError());
        } catch (Exception e) {
            log.error("Error :", e);
        }
    }
    @Test
    @DisplayName("test le end point POST/auth/register Empty")
    void postRegisterEmpty(){
        try {
            mockMvc.perform(post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\" : \"\",\"lastName\" : \"\",\"mail\" : \"\",\"password\" : \"\"}"))
                    .andExpect(status().is4xxClientError());
        } catch (Exception e) {
            log.error("Error :", e);
        }
    }


    @Test
    @DisplayName("test le end point POST/auth/login")
    void postLogin(){
        try {
            mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content("{\"mail\" : \"test@test.fr\",\"password\" : \"test2@O.fr\"}"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("Error :", e);
        }
    }

    @AfterEach
    void setupdelete(){
        profilRepository.deleteAll();
    }

}
