package br.com.nunes.vacancy.management.modules.candidate;

import br.com.nunes.vacancy.management.exceptions.JobNotFoundException;
import br.com.nunes.vacancy.management.exceptions.UserNotFoundException;
import br.com.nunes.vacancy.management.modules.applyjob.ApplyJob;
import br.com.nunes.vacancy.management.modules.applyjob.ApplyJobRepository;
import br.com.nunes.vacancy.management.modules.jobs.Job;
import br.com.nunes.vacancy.management.modules.jobs.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CandidateServiceTest {

    @InjectMocks
    private CandidateService candidateService;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should not be able to apply for a job with a candidate not found")
    public void shouldNotBeAbleToApplyJobWithCandidateNotFound() {
        try{
            candidateService.candidateJobApplication(null, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should not be able to apply for a job with a job not found")
    public void shouldNotBeAbleToApplyJobWithJobNotFound() {
        UUID canididateId = UUID.randomUUID();

        when(candidateRepository.findById(canididateId))
            .thenReturn(Optional.of(new Candidate()));

        try {
            candidateService.candidateJobApplication(canididateId, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should apply for a job when candidate and job exist")
    void shouldApplyForAJobSuccessfully() {
        UUID candidateId = UUID.randomUUID();
        UUID jobId = UUID.randomUUID();

        Candidate candidate = new Candidate();
        candidate.setId(candidateId);

        Job job = new Job();
        job.setId(jobId);

        when(candidateRepository.findById(candidateId))
                .thenReturn(Optional.of(candidate));

        when(jobRepository.findById(jobId))
                .thenReturn(Optional.of(job));

        when(applyJobRepository.save(any(ApplyJob.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ApplyJob result = candidateService.candidateJobApplication(candidateId, jobId);

        assertThat(result).isNotNull();
        assertThat(result.getCandidateId()).isEqualTo(candidateId);
        assertThat(result.getJobId()).isEqualTo(jobId);

        ArgumentCaptor<ApplyJob> captor = ArgumentCaptor.forClass(ApplyJob.class);
        verify(applyJobRepository).save(captor.capture());

        ApplyJob saved = captor.getValue();
        assertThat(saved.getCandidateId()).isEqualTo(candidateId);
        assertThat(saved.getJobId()).isEqualTo(jobId);
    }
}
