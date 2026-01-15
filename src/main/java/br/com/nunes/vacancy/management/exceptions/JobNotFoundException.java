package br.com.nunes.vacancy.management.exceptions;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException(String message) {
        super(message);
    }
}
