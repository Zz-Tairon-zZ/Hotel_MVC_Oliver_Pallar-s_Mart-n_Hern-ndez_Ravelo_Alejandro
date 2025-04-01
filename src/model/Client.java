package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Client {
    private final String id;
    private final String fullName;
    private final List<Reservation> reservations;

    public Client(String fullName) {
        this.id = UUID.randomUUID().toString();
        this.fullName = fullName;
        this.reservations = new ArrayList<>();
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }


    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }


    // Métodos para obtener reservas activas e historial
    public List<Reservation> getActiveReservations() {
        return reservations.stream()
                .filter(r -> !r.isCancelled() && r.getCheckOutDate().isAfter(java.time.LocalDate.now()))
                .collect(Collectors.toList());
    }

    public List<Reservation> getReservationHistory() {
        return reservations.stream()
                .filter(r -> r.isCancelled() || r.getCheckOutDate().isBefore(java.time.LocalDate.now()))
                .collect(Collectors.toList());
    }

    public boolean canMakeReservation() {
        return getActiveReservations().size() < 3;
    }

    @Override
    public String toString() {
        return "Cliente: " + fullName + " (ID: " + id + ")";
    }
}