package br.com.nunes.vacancy.management.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
