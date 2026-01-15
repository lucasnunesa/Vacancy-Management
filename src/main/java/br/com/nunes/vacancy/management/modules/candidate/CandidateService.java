package br.com.nunes.vacancy.management.modules.candidate;

import br.com.nunes.vacancy.management.dto.CandidateResponseDTO;
import br.com.nunes.vacancy.management.exceptions.JobNotFoundException;
import br.com.nunes.vacancy.management.exceptions.UserAlreadyExistException;
import br.com.nunes.vacancy.management.exceptions.UserNotFoundException;
import br.com.nunes.vacancy.management.modules.applyjob.ApplyJob;
import br.com.nunes.vacancy.management.modules.applyjob.ApplyJobRepository;
import br.com.nunes.vacancy.management.modules.jobs.Job;
import br.com.nunes.vacancy.management.modules.jobs.JobRepository;
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

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public Candidate addCandidate (Candidate candidate) {
        validateUsername(candidate.getUsername());
        validatePassword(candidate.getPassword());

        String encodedPassword = passwordEncoder.encode(candidate.getPassword());
        candidate.setPassword(encodedPassword);

        return candidateRepository.save(candidate);
    }

    public CandidateResponseDTO getAuthenticadeCandidate (UUID id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Candidate not found with id: " + id));

        validateCandidate(id);

        CandidateResponseDTO candidateDTO = CandidateResponseDTO.builder()
                .username(candidate.getUsername())
                .name(candidate.getName())
                .email(candidate.getEmail())
                .curriculum(candidate.getCurriculum())
                .build();

        return candidateDTO;
    }

    public ApplyJob candidateJobApplication (UUID candidateId, UUID jobId) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new UserNotFoundException("Candidate not found with id: " + candidateId));

        validateCandidate(candidateId);

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException("Job not found with id: " + jobId));


        ApplyJob applyJob = ApplyJob.builder()
                .candidateId(candidate.getId())
                .jobId(job.getId())
                .build();

        applyJobRepository.save(applyJob);

        return applyJob;
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
