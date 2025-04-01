package controller;

import model.Client;
import model.Reservation;
import model.Room;
import model.ReservaNoDisponibleException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ReserveController {
    private static final int MAX_RESERVATION_DAYS = 90;
    private final List<Reservation> reservations;
    private final RoomController roomController;
    private final ClientController clientController;

    public ReserveController(RoomController roomController, ClientController clientController) {
        this.reservations = new ArrayList<>();
        this.roomController = roomController;
        this.clientController = clientController;
    }

    public Reservation createReservation(String clientId, int roomNumber, LocalDate checkInDate, LocalDate checkOutDate)
            throws ReservaNoDisponibleException {

        // Validar cliente
        Client client = clientController.getClientById(clientId);
        if (client == null) {
            throw new IllegalArgumentException("Cliente no encontrado");
        }

        // Validar si el cliente puede hacer más reservas
        if (!client.canMakeReservation()) {
            throw new ReservaNoDisponibleException("El cliente ya tiene el máximo de 3 reservas activas");
        }

        // Validar habitación
        Room room = roomController.getRoomByNumber(roomNumber);
        if (room == null) {
            throw new IllegalArgumentException("Habitación no encontrada");
        }

        // Validar estado de la habitación
        if (room.getStatus() != Room.RoomStatus.DISPONIBLE) {
            throw new ReservaNoDisponibleException("La habitación no está disponible");
        }

        // Validar fechas
        validateDates(checkInDate, checkOutDate);

        // Validar que no haya solapamiento con otras reservas
        if (hasOverlap(room, checkInDate, checkOutDate)) {
            throw new ReservaNoDisponibleException("Ya existe una reserva para esa habitación en las fechas seleccionadas");
        }

        // Crear la reserva
        Reservation reservation = new Reservation(room, client, checkInDate, checkOutDate);

        // Actualizar estados
        room.setStatus(Room.RoomStatus.RESERVADA);
        client.addReservation(reservation);
        reservations.add(reservation);

        return reservation;
    }

    public void cancelReservation(String reservationId) {
        Reservation reservation = getReservationById(reservationId);
        if (reservation == null) {
            throw new IllegalArgumentException("Reserva no encontrada");
        }

        if (reservation.isCancelled()) {
            throw new IllegalStateException("La reserva ya está cancelada");
        }

        if (LocalDate.now().isAfter(reservation.getCheckInDate())) {
            throw new IllegalStateException("No se puede cancelar una reserva que ya ha comenzado");
        }

        reservation.cancel();
    }

    public void checkIn(String reservationId) {
        Reservation reservation = getReservationById(reservationId);
        if (reservation == null) {
            throw new IllegalArgumentException("Reserva no encontrada");
        }

        if (reservation.isCancelled()) {
            throw new IllegalStateException("No se puede hacer check-in en una reserva cancelada");
        }

        LocalDate today = LocalDate.now();
        if (today.isBefore(reservation.getCheckInDate())) {
            throw new IllegalStateException("No se puede hacer check-in antes de la fecha reservada");
        }

        if (today.isAfter(reservation.getCheckOutDate())) {
            throw new IllegalStateException("No se puede hacer check-in después de la fecha de salida");
        }

        reservation.getRoom().setStatus(Room.RoomStatus.OCUPADA);
    }

    public void checkOut(String reservationId) {
        Reservation reservation = getReservationById(reservationId);
        if (reservation == null) {
            throw new IllegalArgumentException("Reserva no encontrada");
        }

        if (reservation.isCancelled()) {
            throw new IllegalStateException("No se puede hacer check-out en una reserva cancelada");
        }

        if (reservation.getRoom().getStatus() != Room.RoomStatus.OCUPADA) {
            throw new IllegalStateException("No se puede hacer check-out si no se ha hecho check-in");
        }

        reservation.getRoom().setStatus(Room.RoomStatus.DISPONIBLE);
    }

    public Reservation getReservationById(String reservationId) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(reservationId))
                .findFirst()
                .orElse(null);
    }

    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }


    private void validateDates(LocalDate checkInDate, LocalDate checkOutDate) {
        LocalDate today = LocalDate.now();

        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }

        if (checkInDate.isBefore(today)) {
            throw new IllegalArgumentException("No se pueden hacer reservas en fechas pasadas");
        }

        if (checkOutDate.isBefore(checkInDate) || checkOutDate.isEqual(checkInDate)) {
            throw new IllegalArgumentException("La fecha de salida debe ser posterior a la de entrada");
        }

        long days = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        if (days > MAX_RESERVATION_DAYS) {
            throw new IllegalArgumentException("La reserva no puede exceder los " + MAX_RESERVATION_DAYS + " días");
        }
    }

    private boolean hasOverlap(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        return reservations.stream()
                .filter(r -> !r.isCancelled() && r.getRoom().getRoomNumber() == room.getRoomNumber())
                .anyMatch(r -> r.overlaps(checkInDate, checkOutDate));
    }
}