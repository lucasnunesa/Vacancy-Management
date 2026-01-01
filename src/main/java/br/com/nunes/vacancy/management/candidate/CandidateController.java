package br.com.nunes.vacancy.management.candidate;

import br.com.nunes.vacancy.management.dto.CandidateResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping
    public ResponseEntity<Object> addCandidate(@RequestBody @Valid Candidate candidate) {
       try {
           Candidate result = this.candidateService.addCandidate(candidate);
           return ResponseEntity.ok(result);
       } catch (Exception e) {
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> getCandidateById(HttpServletRequest request) {
        String subject = request.getAttribute("candidate_id").toString();
        UUID authenticatedUserId = UUID.fromString(subject);

        try {
            CandidateResponseDTO candidate = this.candidateService.getAuthenticadeCandidate(authenticatedUserId);
            return ResponseEntity.ok(candidate);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
