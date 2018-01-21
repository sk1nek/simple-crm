package me.mjaroszewicz.crmapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long clientId;

    private String orderName;

    private String description;

    private double value;

    private String dateDeadline;
}
