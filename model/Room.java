package model;

public class Room {
    // Enum para el tipo de habitación
    public enum RoomType {
        INDIVIDUAL(50),
        DOBLE(80),
        SUITE(150);

        private final int pricePerNight;

        RoomType(int pricePerNight) {
            this.pricePerNight = pricePerNight;
        }

        public int getPricePerNight() {
            return pricePerNight;
        }
    }

    // Enum para el estado de la habitación
    public enum RoomStatus {
        DISPONIBLE,
        RESERVADA,
        OCUPADA
    }

    private final int roomNumber;
    private final RoomType type;
    private RoomStatus status;
    private final String description;

    public Room(int roomNumber, RoomType type, String description) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.status = RoomStatus.DISPONIBLE;
        this.description = description;
    }

    // Getters y setters
    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public int getPricePerNight() {
        return type.getPricePerNight();
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Habitación " + roomNumber +
                " (" + type + ") - " +
                status +
                " - " + getPricePerNight() + "€/noche" +
                (description != null && !description.isEmpty() ? " - " + description : "");
    }
}