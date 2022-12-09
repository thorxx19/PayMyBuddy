package com.Pay.paymybuddy.model.dto;

import com.Pay.paymybuddy.model.Profil;
import lombok.Data;

@Data
public class TransfertDto {

   private Long id;

   private String description;

   private String amount;

   private Profil idDebtor;

   private Profil idCredit;

}
