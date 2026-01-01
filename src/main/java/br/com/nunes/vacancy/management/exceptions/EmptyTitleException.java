package br.com.nunes.vacancy.management.exceptions;

public class EmptyTitleException extends RuntimeException {
    public EmptyTitleException(String message) {
        super(message);
    }
}
