package me.mjaroszewicz.crmapp.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // -1 for accepted, 0 for in-progress, 1 for realized
    private int state;

    private String description;

    private double value;

    @ManyToOne
    private Client recipient;

    @OneToMany
    @JoinTable(name = "payments")
    @ElementCollection(targetClass = Payment.class)
    private List<Payment> payments = Collections.emptyList();

    private long dateReceived;

    private long dateDeadline;




}
