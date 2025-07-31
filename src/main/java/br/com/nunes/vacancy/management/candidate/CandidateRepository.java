package br.com.nunes.vacancy.management.candidate;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<Candidate, UUID> {

    Optional<Candidate> findByUsername(String username);
    Optional<Candidate> findByEmail(String email);
}
