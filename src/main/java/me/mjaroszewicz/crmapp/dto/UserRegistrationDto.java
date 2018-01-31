package me.mjaroszewicz.crmapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.mjaroszewicz.crmapp.annotations.ValidUserRegistration;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidUserRegistration
public class UserRegistrationDto {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;
}
