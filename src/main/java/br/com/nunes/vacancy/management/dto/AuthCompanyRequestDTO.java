package br.com.nunes.vacancy.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCompanyRequestDTO {

    private String username;
    private String password;
}
