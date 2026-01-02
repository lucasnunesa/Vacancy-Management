package br.com.nunes.vacancy.management.jobs;

import br.com.nunes.vacancy.management.company.Company;
import br.com.nunes.vacancy.management.company.CompanyService;
import br.com.nunes.vacancy.management.dto.JobRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/job")
@Tag(name = "Job Management", description = "Manage job postings for companies")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private CompanyService companyService;

    @PostMapping("/company")
    @PreAuthorize("hasRole('COMPANY')")
    @Operation(summary = "Create a new job", description = "Create a new job posting for the company. The company ID is automatically set based on the authenticated user's company.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job created successfully", content = {@Content (schema = @Schema(implementation = Job.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid job data provided"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "403", description = "Forbidden access"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Job> createJob (@RequestBody JobRequestDTO jobRequestDTO, HttpServletRequest request) {
        Object requestCompanyId = request.getAttribute("company_id");
        UUID companyId = UUID.fromString(requestCompanyId.toString());

        Company company = companyService.getCompanyById(companyId);

        Job job = Job.builder()
                .description(jobRequestDTO.getDescription())
                .title(jobRequestDTO.getTitle())
                .level(jobRequestDTO.getLevel())
                .company(company)
                .companyId(company.getId())
                .build();

        Job createdJob = jobService.createJob(job);

        return ResponseEntity.ok(createdJob);
    }

    @GetMapping("/candidate/filter")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "List all jobs by filter", description = "Retrieve a list of all jobs based on the provided filter. If no filter is provided, all jobs are returned.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Jobs retrieved successfully", content = {@Content (schema = @Schema(implementation = Job.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "403", description = "Forbidden access"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<?> listAllJobsByFilter(@RequestParam(required = false) String filter) {

        return ResponseEntity.ok(jobService.listAllJobsByFilter(filter));
    }

}
