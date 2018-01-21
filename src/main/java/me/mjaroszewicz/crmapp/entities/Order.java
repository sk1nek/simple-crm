package me.mjaroszewicz.crmapp.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;


@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // -1 for accepted, 0 for in-progress, 1 for realized
    @NonNull private int state;

    @NonNull private String name;

    @NonNull private String description;

    @NonNull private double value;

    @ManyToOne
    @NonNull private Client recipient;

    @OneToMany
    @JoinTable(name = "payments")
    @ElementCollection(targetClass = Payment.class)
    private List<Payment> payments = Collections.emptyList();

    @NonNull private String dateReceived;

    @NonNull private String dateDeadline;

    private int paymentPercentage;




}
