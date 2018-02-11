package me.mjaroszewicz.crmapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusChangeDto {

    @NotNull
    private Long targetId;

    @NotNull
    private Integer state;

}
