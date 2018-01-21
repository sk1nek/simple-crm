package me.mjaroszewicz.crmapp.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table( name = "payments")
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

    private double amount;

    private String description;

    @Column(name = "order_id")
    private long parentOrderId;

}
