package com.tcc.Hive.security.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAuthenticationDto {
    private String companyEmail;
    private String password;
}
