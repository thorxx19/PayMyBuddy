package com.Pay.PayMyBuddy.model;


import lombok.Data;

@Data
public class AuthResponse {

    String message;
    Long userId;
    String accessToken;

}
