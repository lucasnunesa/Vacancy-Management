package br.com.nunes.vacancy.management.auth;

import br.com.nunes.vacancy.management.candidate.Candidate;
import br.com.nunes.vacancy.management.candidate.CandidateRepository;
import br.com.nunes.vacancy.management.dto.AuthCandidateRequestDTO;
import br.com.nunes.vacancy.management.dto.AuthCandidateResponseDTO;
import br.com.nunes.vacancy.management.exceptions.UserAlreadyExistException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCandidate {

    @Value("security.token.secret")
    private String secret;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO authenticate(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
        Candidate candidate = candidateRepository.findByUsername(authCandidateRequestDTO.username())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Username / Password invalid");
                });

        boolean password = this.passwordEncoder
                .matches(authCandidateRequestDTO.password(), candidate.getPassword());

        if (!password) {
            throw new AuthenticationException("Username / Password invalid") {
                @Override
                public String getMessage() {
                    return "Username / Password invalid";
                }
            };
        }

        Algorithm algorithm = Algorithm.HMAC256(secret);

        String token = JWT.create().withIssuer("vacancy-manager")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withClaim("roles", Arrays.asList("ROLE_CANDIDATE"))
                .withSubject(candidate.getId().toString())
                .sign(algorithm);

        AuthCandidateResponseDTO authResponse = AuthCandidateResponseDTO.builder()
        .token(token)
        .build();

        return authResponse;
    }
}
