package me.mjaroszewicz.crmapp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

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

    public String getStringDate() {

        LocalDateTime date = LocalDateTime.ofEpochSecond(dateMilis / 1000, 0, ZoneOffset.UTC);
        return date.format(DateTimeFormatter.ISO_DATE_TIME);
    }

}
