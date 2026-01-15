package br.com.nunes.vacancy.management.modules.applyjob;

import br.com.nunes.vacancy.management.modules.candidate.Candidate;
import br.com.nunes.vacancy.management.modules.jobs.Job;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ApplyJob {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", insertable = false, updatable = false)
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "job_id", insertable = false, updatable = false)
    private Job job;

    @Column(name = "job_id", nullable = false)
    private UUID jobId;

    @Column(name = "candidate_id", nullable = false)
    private UUID candidateId;

    @CurrentTimestamp
    private LocalDateTime createdAt;

    @CurrentTimestamp
    private LocalDateTime updatedAt;
}
