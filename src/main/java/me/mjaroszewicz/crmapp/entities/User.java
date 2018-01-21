package me.mjaroszewicz.crmapp.entities;

import lombok.Data;
import lombok.Generated;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String email;

    private String phone;

    @ElementCollection(targetClass = String.class)
    private Set<String> permissions;


    private boolean active;


}
