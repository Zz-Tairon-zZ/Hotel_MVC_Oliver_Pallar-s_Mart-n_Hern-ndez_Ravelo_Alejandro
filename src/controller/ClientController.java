package controller;

import model.Client;
import model.Reservation;
import java.util.ArrayList;
import java.util.List;

public class ClientController {
    private final List<Client> clients;

    public ClientController() {
        this.clients = new ArrayList<>();
    }

    public Client createClient(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo no puede estar vacío");
        }

        Client client = new Client(fullName);
        clients.add(client);
        return client;
    }

    public List<Client> getAllClients() {
        return new ArrayList<>(clients);
    }

    public Client getClientById(String id) {
        return clients.stream()
                .filter(client -> client.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Client getClientByName(String name) {
        return clients.stream()
                .filter(client -> client.getFullName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public List<Reservation> getClientActiveReservations(String clientId) {
        Client client = getClientById(clientId);
        if (client == null) {
            return new ArrayList<>();
        }
        return client.getActiveReservations();
    }

    public List<Reservation> getClientReservationHistory(String clientId) {
        Client client = getClientById(clientId);
        if (client == null) {
            return new ArrayList<>();
        }
        return client.getReservationHistory();
    }

    public boolean canClientMakeReservation(String clientId) {
        Client client = getClientById(clientId);
        if (client == null) {
            return false;
        }
        return client.canMakeReservation();
    }

    public String generateClientSummary() {
        StringBuilder summary = new StringBuilder("RESUMEN DE CLIENTES\n");
        summary.append("==================\n\n");

        if (clients.isEmpty()) {
            summary.append("No hay clientes registrados.");
            return summary.toString();
        }

        for (Client client : clients) {
            summary.append(client.toString()).append("\n");

            List<Reservation> activeReservations = client.getActiveReservations();
            if (!activeReservations.isEmpty()) {
                summary.append("  Reservas activas:\n");
                for (Reservation reservation : activeReservations) {
                    summary.append("  - Habitación: ").append(reservation.getRoom().getRoomNumber())
                            .append(", Check-in: ").append(reservation.getCheckInDate())
                            .append(", Check-out: ").append(reservation.getCheckOutDate())
                            .append("\n");
                }
            } else {
                summary.append("  Sin reservas activas.\n");
            }
            summary.append("\n");
        }

        return summary.toString();
    }
}