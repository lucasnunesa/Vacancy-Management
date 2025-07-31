package br.com.nunes.vacancy.management.company;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity (name = "company")
@Data
public class Company {

    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    private String username;

    private String password;

    private String name;

    @Email (message = "Insira um e-mail válido.")
    private String email;

    private String description;

    @CurrentTimestamp
    private LocalDateTime createdAt;

    @CurrentTimestamp
    private LocalDateTime updatedAt;
}
