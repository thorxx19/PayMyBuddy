package com.Pay.PayMyBuddy.model;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class AccountRequest {

    private Long id;

    private BigDecimal balance;

}
