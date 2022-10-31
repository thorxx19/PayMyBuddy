package com.Pay.PayMyBuddy.model;

import lombok.Data;

@Data
public class RefreshRequest {

    Long profilId;
    String refreshToken;
}