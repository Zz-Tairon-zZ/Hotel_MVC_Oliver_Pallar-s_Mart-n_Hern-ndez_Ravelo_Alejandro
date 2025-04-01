package model;

public class ReservaNoDisponibleException extends Exception {
    public ReservaNoDisponibleException(String message) {
        super(message);
    }
}