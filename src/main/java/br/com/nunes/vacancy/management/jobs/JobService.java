package br.com.nunes.vacancy.management.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public Job createJob (Job job) {
        return jobRepository.save(job);
    }
}
