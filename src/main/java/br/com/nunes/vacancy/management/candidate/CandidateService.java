package br.com.nunes.vacancy.management.candidate;

import br.com.nunes.vacancy.management.exceptions.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {

    @Autowired
    protected CandidateRepository candidateRepository;

    public Candidate addCandidate (Candidate candidate) {
        validateUsername(candidate.getUsername());

        return candidateRepository.save(candidate);
    }

    private void validateUsername (String username) {
        this.candidateRepository.findByUsername(username).ifPresent(candidate -> {
            throw new UserAlreadyExistException();
        });
    }
}
