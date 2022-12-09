package com.Pay.paymybuddy.model;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserRequest {

    @NotEmpty
    String password;

    @NotEmpty
    String mail;

}
