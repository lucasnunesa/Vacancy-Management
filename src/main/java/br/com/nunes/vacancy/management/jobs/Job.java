package br.com.nunes.vacancy.management.jobs;

import br.com.nunes.vacancy.management.company.Company;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity (name = "job")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Job {

    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String description;

    private String level;

    @ManyToOne
    @JoinColumn (name = "company_id", insertable = false, updatable = false)
    private Company company;

    @Column (name = "company_id", nullable = false)
    private UUID companyId;

    @CurrentTimestamp
    private LocalDateTime createdAt;

    @CurrentTimestamp
    private LocalDateTime updatedAt;
}
