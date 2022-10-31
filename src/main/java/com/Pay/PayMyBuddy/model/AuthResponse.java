package com.Pay.PayMyBuddy.model;


import lombok.Data;

import java.util.List;

@Data
public class AuthResponse {

    String message;
    Long userId;
    String accessToken;
    List<?> datas;
    Object data;

}
