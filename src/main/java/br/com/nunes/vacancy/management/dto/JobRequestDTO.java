package br.com.nunes.vacancy.management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class JobRequestDTO {

    @Schema(example = "Senior Java Developer")
    private String description;

    @Schema(example = "Java Developer")
    private String title;

    @Schema(example = "Senior")
    private String level;
}
