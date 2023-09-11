package com.tcc.Hive.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class UserAuthenticationDTO {
    private String userName;
    private String password;
    private ArrayList<String> roles;
}
