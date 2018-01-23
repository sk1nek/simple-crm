package me.mjaroszewicz.crmapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private String name;

    private String description;

    private String address;

    private String phone;

    private String email;

}
