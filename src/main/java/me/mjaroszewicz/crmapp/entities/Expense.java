package me.mjaroszewicz.crmapp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@NoArgsConstructor
public class Expense {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private double value;

    @NonNull
    private String description;

    @NonNull
    private long dateMilis;

    /**
     *
     * @return Creation date formatted in ISO format.
     */
    public String getStringDate(){
        LocalDateTime date = LocalDateTime.ofEpochSecond(dateMilis, 0, ZoneOffset.UTC);
        return date.format(DateTimeFormatter.ISO_DATE);
    }

}
