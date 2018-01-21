package me.mjaroszewicz.crmapp.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table( name = "payments")
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

    private double amount;

    private String description;

}
