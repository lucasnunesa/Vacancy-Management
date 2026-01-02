package br.com.nunes.vacancy.management.modules.jobs;

import br.com.nunes.vacancy.management.exceptions.EmptyDescriptionException;
import br.com.nunes.vacancy.management.exceptions.EmptyTitleException;
import br.com.nunes.vacancy.management.exceptions.InvalidFilterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public Job createJob (Job job) {
        validateTitle(job.getTitle());

        validateDescription(job.getDescription());

        return jobRepository.save(job);
    }

    public List <Job> listAllJobsByFilter (String filter) {
       validateFilter(filter);

        if (filter == null || filter.isEmpty()) {
            return jobRepository.findAll();
        }

        return jobRepository.findByDescriptionContainingIgnoreCase(filter);
    }

    private void validateTitle (String title) {
        if (title == null || title.isEmpty()) {
            throw new EmptyTitleException("Job title cannot be null or empty");
        }
    }

    private void validateDescription (String description) {
        if (description == null || description.isEmpty()) {
            throw new EmptyDescriptionException("Job description cannot be null or empty");
        }
    }

    private void validateFilter (String filter) {
        if (filter != null && filter.length() > 100) {
            throw new InvalidFilterException("Filter cannot be longer than 100 characters");
        }
    }
}
