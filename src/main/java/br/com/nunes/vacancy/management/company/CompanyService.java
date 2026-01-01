package br.com.nunes.vacancy.management.company;

import br.com.nunes.vacancy.management.exceptions.CompanyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Company createCompany(Company company) {

        String password = passwordEncoder.encode(company.getPassword());
        company.setPassword(password);

        return companyRepository.save(company);
    }

    public Company getCompanyById (UUID id) {
        return companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException("Company not found"));
    }
}
