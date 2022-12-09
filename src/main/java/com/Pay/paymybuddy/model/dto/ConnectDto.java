package com.Pay.paymybuddy.model.dto;

import lombok.Data;


import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
public class ConnectDto {


    @NotEmpty
    private UUID idDeux;

}
