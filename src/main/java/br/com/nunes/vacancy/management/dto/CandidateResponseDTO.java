package br.com.nunes.vacancy.management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateResponseDTO {

    private String username;
    private String name;
    private String email;
    private String curriculum;
}
