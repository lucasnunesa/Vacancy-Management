package br.com.nunes.vacancy.management.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping
    public ResponseEntity<Object> addCandidate(@RequestBody Candidate candidate) {
       try {
           Candidate result = this.candidateService.addCandidate(candidate);
           return ResponseEntity.ok(result);
       } catch (Exception e) {
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }

}
