package br.com.nunes.vacancy.management.auth;

import br.com.nunes.vacancy.management.dto.AuthCompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthCompany authCompany;

    @PostMapping("/company")
    public ResponseEntity<Object> authCompany(@RequestBody final AuthCompanyDTO authCompanyDto) {
        try{
            String result = this.authCompany.auth(authCompanyDto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
