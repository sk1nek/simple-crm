package me.mjaroszewicz.crmapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.mjaroszewicz.crmapp.annotations.ValidDate;
import me.mjaroszewicz.crmapp.annotations.ValidOrderDto;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidOrderDto
public class OrderDto {

    private Long clientId;

    @NotNull
    private String orderName;

    private String description;

    @NotNull
    private double value;

    @NotNull
    @ValidDate
    private String dateDeadline;
}
