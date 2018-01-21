package me.mjaroszewicz.crmapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;


@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // -1 for accepted, 0 for in-progress, 1 for realized
    private int state;

    private String name;

    private String description;

    private double value;

    @ManyToOne
    private Client recipient;

    @OneToMany
    @JoinTable(name = "payments")
    @ElementCollection(targetClass = Payment.class)
    private List<Payment> payments = Collections.emptyList();


    private String dateReceived;

    private String dateDeadline;




}
