package me.mjaroszewicz.crmapp.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserRegistrationDto {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;
}
