package me.mjaroszewicz.crmapp.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Client {

    @GeneratedValue
    @Id
    private Long id;

    private String name;

    private String description;

    private String address;

    private String phone;

    private String email;




}
