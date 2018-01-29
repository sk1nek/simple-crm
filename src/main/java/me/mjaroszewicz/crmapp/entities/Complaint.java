package me.mjaroszewicz.crmapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Data
@Table(name = "complaints")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Complaint {

    @GeneratedValue
    @Id
    private Long id;

    @ManyToOne
    private Order order;

    @ElementCollection(targetClass = String.class)
    @JoinColumn
    private List<String> attachedFiles = Collections.emptyList();

    private String dateCreated;

    private String dateDeadline;

    private String description;

    // -1 , 0 , 1
    private int state;

    private boolean urgent;

    private String result;
}
