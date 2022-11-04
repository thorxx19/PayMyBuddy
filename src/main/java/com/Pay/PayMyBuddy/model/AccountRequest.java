package com.Pay.PayMyBuddy.model;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Data
public class AccountRequest {

    @Digits(integer = 15, fraction = 3)
    private BigDecimal balance;

}
