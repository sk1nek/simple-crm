package me.mjaroszewicz.crmapp.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    // -1 for accepted, 0 for in-progress, 1 for realized
    private int state;

    private String description;

    private double value;

    @ManyToOne
    private Client recipient;

    @ElementCollection(targetClass = Payment.class)
    @OneToOne
    private List<Payment> payments;

    private long dateReceived;

    private long dateDeadline;




}
