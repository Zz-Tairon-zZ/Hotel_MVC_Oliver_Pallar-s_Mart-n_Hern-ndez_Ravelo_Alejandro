package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Reservation {
    private final String id;
    private final Room room;
    private final Client client;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;
    private final double totalPrice;
    private boolean cancelled;

    public Reservation(Room room, Client client, LocalDate checkInDate, LocalDate checkOutDate) {
        this.id = UUID.randomUUID().toString();
        this.room = room;
        this.client = client;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = calculateTotalPrice();
        this.cancelled = false;
    }

    // Getters
    public String getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }


    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }


    public boolean isCancelled() {
        return cancelled;
    }

    // Métodos de negocio
    public void cancel() {
        if (LocalDate.now().isBefore(checkInDate)) {
            cancelled = true;
            room.setStatus(Room.RoomStatus.DISPONIBLE);
        } else {
            throw new IllegalStateException("No se puede cancelar una reserva que ya ha comenzado");
        }
    }

    public long getDurationInDays() {
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }

    private double calculateTotalPrice() {
        return getDurationInDays() * room.getPricePerNight();
    }

    public boolean overlaps(LocalDate start, LocalDate end) {
        return !cancelled &&
                ((start.isBefore(checkOutDate) || start.isEqual(checkOutDate)) &&
                        (end.isAfter(checkInDate) || end.isEqual(checkInDate)));
    }

    @Override
    public String toString() {
        return "Reserva ID: " + id +
                "\nCliente: " + client.getFullName() +
                "\nHabitación: " + room.getRoomNumber() +
                "\nCheck-in: " + checkInDate +
                "\nCheck-out: " + checkOutDate +
                "\nDuración: " + getDurationInDays() + " días" +
                "\nPrecio total: " + totalPrice + "€" +
                (cancelled ? "\nESTADO: CANCELADA" : "");
    }
}