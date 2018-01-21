package me.mjaroszewicz.crmapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clients")
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
