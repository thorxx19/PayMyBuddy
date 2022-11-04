package com.Pay.PayMyBuddy.model;

import lombok.Data;

import java.util.Date;

@Data
public class TransfertDto {

   private Long id;

   private String description;

   private String amount;

   private Profil idDebtor;

   private Profil idCredit;

}
