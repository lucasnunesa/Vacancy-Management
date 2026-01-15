package br.com.nunes.vacancy.management.modules.candidate;

import br.com.nunes.vacancy.management.dto.CandidateResponseDTO;
import br.com.nunes.vacancy.management.modules.applyjob.ApplyJob;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidate", description = "Candidate management endpoints")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping
    @Operation(summary = "Add a new candidate", description = "Create a new candidate in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidate created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid candidate data provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
    @Operation(summary = "Get authenticated candidate", description = "Retrieve the candidate information for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidate retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "403", description = "Forbidden access"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @PostMapping("/apply/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @SecurityRequirement(name = "jwt_auth")
    @Operation(summary = "Apply for a job", description = "Allows an authenticated candidate to apply for a specific job")
    public ResponseEntity<Object> applyForJob(HttpServletRequest request,
                                              @RequestBody @Valid
                                              UUID jobId) {
        String subject = request.getAttribute("candidate_id").toString();
        UUID authenticatedUserId = UUID.fromString(subject);

        try {
            ApplyJob applyJob = this.candidateService.candidateJobApplication(authenticatedUserId, jobId);
            return ResponseEntity.ok(applyJob);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
