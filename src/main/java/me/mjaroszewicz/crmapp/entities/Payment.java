package me.mjaroszewicz.crmapp.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

    private double amount;

    private String description;

}
