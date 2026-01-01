package br.com.nunes.vacancy.management;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Vacancy Management API",
				version = "1.0.0",
				description = "API for managing job vacancies of companys and candidates."
		)
)
@SecurityScheme(name = "jwt_auth", scheme = "bearer", bearerFormat = "JWT", type = io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
