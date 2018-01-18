package me.mjaroszewicz.crmapp.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Complaint {

    @GeneratedValue
    @Id
    private Long id;

    @ManyToOne
    private Order order;

    @ElementCollection(targetClass = String.class)
    @OneToOne
    private List<String> attachedFiles;

    private long dateCreated;

    private long dateDeadline;

    private String description;

    // -1 , 0 , 1
    private int state;

    private boolean urgent;

    private String result;
}
