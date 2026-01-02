package br.com.nunes.vacancy.management.auth;

import br.com.nunes.vacancy.management.modules.company.Company;
import br.com.nunes.vacancy.management.modules.company.CompanyRepository;
import br.com.nunes.vacancy.management.dto.AuthCompanyRequestDTO;
import br.com.nunes.vacancy.management.dto.AuthCompanyResponseDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class AuthCompany {

    @Value("${security.token.secret}")
    private String secret;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO auth(AuthCompanyRequestDTO authCompanyRequestDTO) {
        Company company = this.companyRepository.findByUsername(authCompanyRequestDTO.getUsername());

        if (company == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        boolean password = this.passwordEncoder.matches(authCompanyRequestDTO.getPassword(), company.getPassword());

        if (!password) {
            throw new BadCredentialsException("Bad credentials");
        }

        Algorithm algorithm = Algorithm.HMAC256(secret);

        Instant expirationTime = Instant.now().plus(Duration.ofHours(2));

        String token = JWT.create().withIssuer("vacancy-manager")
        .withExpiresAt(expirationTime)
        .withSubject(company.getId().toString())
        .withClaim("roles", List.of("COMPANY"))
        .sign(algorithm);

        AuthCompanyResponseDTO response = AuthCompanyResponseDTO.builder()
                .token(token)
                .expirationTime(expirationTime)
                .build();

        return response;
    }
}
