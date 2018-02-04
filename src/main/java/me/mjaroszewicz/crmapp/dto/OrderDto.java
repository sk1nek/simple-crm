package me.mjaroszewicz.crmapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long clientId;

    @NotNull
    @Length(min = 3, max = 100)
    private String orderName;

    private String description;

    @NotNull
    private double value;

    @NotNull
    private String dateDeadline;
}
