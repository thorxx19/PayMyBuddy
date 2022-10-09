package com.Pay.PayMyBuddy.controller;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class ConnectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("test le end point GET/connect")
    public void getConnectAll(){
        try {
            mockMvc.perform(get("/connect"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }
    


}
