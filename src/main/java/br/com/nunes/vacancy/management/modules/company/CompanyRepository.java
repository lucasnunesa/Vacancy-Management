package br.com.nunes.vacancy.management.modules.company;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Company findByUsername(String username);
}
