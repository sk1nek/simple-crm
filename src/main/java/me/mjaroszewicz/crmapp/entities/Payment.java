package me.mjaroszewicz.crmapp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
//@Table(name = "payments")
@NoArgsConstructor
@RequiredArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private double amount;

    @NonNull
    private String description;

    @Column(name = "order_id")
    private long parentOrderId;

    @NonNull
    private long dateMilis;

}
