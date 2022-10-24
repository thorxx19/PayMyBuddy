package com.Pay.PayMyBuddy.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class LoginControllerTest {


    @Autowired
    LoginController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }


}
