package me.mjaroszewicz.crmapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import me.mjaroszewicz.crmapp.annotations.ValidClient;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidClient
public class ClientDto {

    private long id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private String address;

    @NonNull
    private String phone;

    private String email;

}
