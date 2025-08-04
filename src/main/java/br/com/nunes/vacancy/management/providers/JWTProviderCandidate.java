package br.com.nunes.vacancy.management.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTProviderCandidate {

    @Value("${security.token.secret.candidate}")
    private String secret;

    public DecodedJWT validateToken(String token) {

        String subjectToken = token.replace("Bearer ", "");

        Algorithm algorithm = Algorithm.HMAC256(secret);

        try {
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .build()
                    .verify(subjectToken);

            return decodedJWT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
