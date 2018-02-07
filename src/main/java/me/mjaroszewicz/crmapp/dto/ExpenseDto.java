package me.mjaroszewicz.crmapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ExpenseDto {

    @NonNull
    private double value;

    @NonNull
    @NotBlank
    private String description;

}
