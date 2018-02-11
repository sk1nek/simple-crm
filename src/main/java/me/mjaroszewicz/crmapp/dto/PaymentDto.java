package me.mjaroszewicz.crmapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class PaymentDto {

    @NotNull
    @NonNull
    private double value;

    @NonNull
    @NotBlank
    private String description;

    @NotNull
    @NonNull
    private long parentOrderId;

}
