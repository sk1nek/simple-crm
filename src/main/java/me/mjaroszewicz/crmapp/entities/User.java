package me.mjaroszewicz.crmapp.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
@RequiredArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String email;

    private String phone;

    @ElementCollection(targetClass = String.class)
    private Set<String> permissions;

    private boolean active;


}
