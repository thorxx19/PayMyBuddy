package com.Pay.paymybuddy.model;

import lombok.Data;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;


@Data
public class AccountRequest {

    @Digits(integer = 15, fraction = 3)
    private BigDecimal balance;

}
