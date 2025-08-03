package br.com.nunes.vacancy.management.candidate;

import br.com.nunes.vacancy.management.exceptions.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Candidate addCandidate (Candidate candidate) {
        validateUsername(candidate.getUsername());
        validatePassword(candidate.getPassword());

        String encodedPassword = passwordEncoder.encode(candidate.getPassword());
        candidate.setPassword(encodedPassword);

        return candidateRepository.save(candidate);
    }

    private void validateUsername (String username) {
        this.candidateRepository.findByUsername(username).ifPresent(candidate -> {
            throw new UserAlreadyExistException();
        });
    }

    private void validatePassword (String password) {
        if (password.length() < 8 || password.length() > 16) {
            throw new IllegalArgumentException("Password must be between 8 and 16 characters.");
        }
    }
}
