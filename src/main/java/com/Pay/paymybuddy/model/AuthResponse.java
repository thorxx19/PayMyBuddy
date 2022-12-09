package com.Pay.paymybuddy.model;


import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class AuthResponse {

    String message;
    UUID userId;
    String accessToken;
    List<?> datas;
    Object data;

}
