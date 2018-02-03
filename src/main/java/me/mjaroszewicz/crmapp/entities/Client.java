package me.mjaroszewicz.crmapp.entities;

import lombok.*;
import me.mjaroszewicz.crmapp.dto.ClientDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "clients")
public class Client {

    @GeneratedValue
    @Id
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private String address;

    private String phone;

    private String email;

    @NonNull
    private long dateCreatedMilis;

    public ClientDto getDto(){
        return new ClientDto(name, description, address, phone, email);
    }

}
