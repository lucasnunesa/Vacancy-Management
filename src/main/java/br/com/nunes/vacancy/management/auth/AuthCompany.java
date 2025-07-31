package br.com.nunes.vacancy.management.auth;

import br.com.nunes.vacancy.management.company.Company;
import br.com.nunes.vacancy.management.company.CompanyRepository;
import br.com.nunes.vacancy.management.dto.AuthCompanyDTO;
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

@Service
public class AuthCompany {

    @Value("security.token.secret")
    private String secret;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String auth(AuthCompanyDTO authCompanyDTO) {
        Company company = this.companyRepository.findByUsername(authCompanyDTO.getUsername());

        if (company == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        boolean password = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        if (!password) {
            throw new BadCredentialsException("Bad credentials");
        }

        Algorithm algorithm = Algorithm.HMAC256(secret);

        String token = JWT.create().withIssuer("vacancy-manager")
        .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
        .withSubject(company.getId().toString())
        .sign(algorithm);

        return token;
    }
}
