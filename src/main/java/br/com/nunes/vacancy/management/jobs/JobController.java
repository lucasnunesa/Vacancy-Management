package br.com.nunes.vacancy.management.jobs;

import br.com.nunes.vacancy.management.dto.JobDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping
    public ResponseEntity<Job> createJob (@RequestBody JobDTO jobDTO, HttpServletRequest request) {
        Object requestCompanyId = request.getAttribute("company_id");

        Job job = Job.builder()
                .description(jobDTO.getDescription())
                .title(jobDTO.getTitle())
                .level(jobDTO.getLevel())
                .companyId(UUID.fromString(requestCompanyId.toString()))
                .build();

        Job createdJob = jobService.createJob(job);

        return ResponseEntity.ok(createdJob);



    }

}
