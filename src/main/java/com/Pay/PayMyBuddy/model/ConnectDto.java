package com.Pay.PayMyBuddy.model;

import lombok.Data;

import javax.validation.constraints.Digits;
import java.util.UUID;

@Data
public class ConnectDto {


    @Digits(integer = 15, fraction = 0)
    private UUID idDeux;

}
