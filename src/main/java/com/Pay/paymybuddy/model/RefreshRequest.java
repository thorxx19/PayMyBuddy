package com.Pay.paymybuddy.model;

import lombok.Data;

@Data
public class RefreshRequest {

    Long profilId;
    String refreshToken;
}