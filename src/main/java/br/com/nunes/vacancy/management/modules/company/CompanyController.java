package br.com.nunes.vacancy.management.modules.company;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@Tag(name = "Company", description = "Company management endpoints")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    @Operation(summary = "Create a new company", description = "Create a new company in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company created successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Company.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid company data provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Company> createCompany (@Valid @RequestBody Company company) {
        Company createdCompany = companyService.createCompany(company);

        return ResponseEntity.ok(createdCompany);
    }
}
