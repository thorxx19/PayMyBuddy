package com.Pay.PayMyBuddy.model;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class PostTransfert {

    private long idDebtor;

    private long idCredit;

    private BigDecimal balance;

    private String descriptif;


}
