package br.com.nunes.vacancy.management.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTProvider {

    @Value("security.token.secret")
    private String secret;

    public String validateToken(String token) {
        token = token.substring(7);

        Algorithm algorithm = Algorithm.HMAC256(secret);

        try {
            String subject = JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();

            return subject;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return "";
        }
    }
}
