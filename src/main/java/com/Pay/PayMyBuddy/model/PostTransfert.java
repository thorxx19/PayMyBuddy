package com.Pay.PayMyBuddy.model;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PostTransfert {


    @NotEmpty
    private UUID idCredit;
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal balance;

    @Length(min = 5, max = 30)
    private String descriptif;


}
