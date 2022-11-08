package com.Pay.PayMyBuddy.model;


import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PostTransfert {


    @Digits(integer = 15, fraction = 0)
    private UUID idCredit;
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal balance;
    @NotEmpty
    private String descriptif;


}
