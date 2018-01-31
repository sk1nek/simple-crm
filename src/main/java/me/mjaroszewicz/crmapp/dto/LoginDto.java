package me.mjaroszewicz.crmapp.dto;

import lombok.Data;
import me.mjaroszewicz.crmapp.annotations.ValidLoginDto;

import javax.validation.constraints.NotNull;

@Data
@ValidLoginDto
public class LoginDto {

    private String username;

    private String password;
}
