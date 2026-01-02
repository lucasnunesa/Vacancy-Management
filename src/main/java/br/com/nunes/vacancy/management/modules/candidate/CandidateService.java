package br.com.nunes.vacancy.management.modules.candidate;

import br.com.nunes.vacancy.management.dto.CandidateResponseDTO;
import br.com.nunes.vacancy.management.exceptions.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    public CandidateResponseDTO getAuthenticadeCandidate (UUID id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Candidate not found with id: " + id));

        validateCandidate(id);

        CandidateResponseDTO candidateDTO = CandidateResponseDTO.builder()
                .username(candidate.getUsername())
                .name(candidate.getName())
                .email(candidate.getEmail())
                .curriculum(candidate.getCurriculum())
                .build();

        return candidateDTO;
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

    private void validateCandidate (UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Candidate ID cannot be null.");
        }
    }

}
