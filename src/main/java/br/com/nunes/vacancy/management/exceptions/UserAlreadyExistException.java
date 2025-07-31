package br.com.nunes.vacancy.management.exceptions;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super("Usuário já cadastrado.");
    }
}
