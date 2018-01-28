package me.mjaroszewicz.crmapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintDto {

    private Long orderId;

    private List<MultipartFile> files;

    private String deadline;

    private String description;

    private boolean urgent;




}
