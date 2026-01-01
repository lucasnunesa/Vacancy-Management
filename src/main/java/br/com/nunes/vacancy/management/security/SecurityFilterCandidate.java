package br.com.nunes.vacancy.management.security;

import br.com.nunes.vacancy.management.providers.JWTProviderCandidate;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class SecurityFilterCandidate extends OncePerRequestFilter {

    @Autowired
    private JWTProviderCandidate jwtProviderCandidate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (request.getRequestURI().startsWith("/candidate") || request.getRequestURI().startsWith("/job/candidate")) {

            if (header != null) {
                DecodedJWT decodedJWT = this.jwtProviderCandidate.validateToken(header);

                if (decodedJWT == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Invalid token");
                    return;
                }

                request.setAttribute("candidate_id", UUID.fromString(decodedJWT.getSubject()));

                List<Object> roles = decodedJWT.getClaim("roles").asList(Object.class);

                List<SimpleGrantedAuthority> grants = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()))
                        .toList();

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken
                        (decodedJWT.getSubject(), null, grants);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
