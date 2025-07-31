package br.com.nunes.vacancy.management.company;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<Company> createCompany (@Valid @RequestBody Company company) {
        Company createdCompany = companyService.createCompany(company);

        return ResponseEntity.ok(createdCompany);
    }
}
