package controller;

import model.Room;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoomController {
    private final List<Room> rooms;

    public RoomController() {
        this.rooms = new ArrayList<>();
        initializeRooms();
    }

    private void initializeRooms() {
        // Inicializar las habitaciones del hotel según los requisitos
        // Planta 1: 101-105
        rooms.add(new Room(101, Room.RoomType.INDIVIDUAL, "Vista al jardín"));
        rooms.add(new Room(102, Room.RoomType.INDIVIDUAL, "Cama extra grande"));
        rooms.add(new Room(103, Room.RoomType.DOBLE, "Dos camas individuales"));
        rooms.add(new Room(104, Room.RoomType.DOBLE, "Cama matrimonial"));
        rooms.add(new Room(105, Room.RoomType.SUITE, "Con jacuzzi"));

        // Planta 2: 201-205
        rooms.add(new Room(201, Room.RoomType.INDIVIDUAL, "Vista a la ciudad"));
        rooms.add(new Room(202, Room.RoomType.DOBLE, "Balcón privado"));
        rooms.add(new Room(203, Room.RoomType.DOBLE, "Cama King Size"));
        rooms.add(new Room(204, Room.RoomType.SUITE, "Sala de estar separada"));
        rooms.add(new Room(205, Room.RoomType.SUITE, "Vista panorámica"));

        // Planta 3: 301-305
        rooms.add(new Room(301, Room.RoomType.INDIVIDUAL, "Terraza pequeña"));
        rooms.add(new Room(302, Room.RoomType.DOBLE, "Vista al mar"));
        rooms.add(new Room(303, Room.RoomType.DOBLE, "Decoración moderna"));
        rooms.add(new Room(304, Room.RoomType.SUITE, "Dos habitaciones"));
        rooms.add(new Room(305, Room.RoomType.SUITE, "Suite presidencial"));
    }

    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms);
    }

    public Room getRoomByNumber(int roomNumber) {
        return rooms.stream()
                .filter(room -> room.getRoomNumber() == roomNumber)
                .findFirst()
                .orElse(null);
    }

    public List<Room> getRoomsByType(Room.RoomType type) {
        return rooms.stream()
                .filter(room -> room.getType() == type)
                .collect(Collectors.toList());
    }

    public List<Room> getRoomsByStatus(Room.RoomStatus status) {
        return rooms.stream()
                .filter(room -> room.getStatus() == status)
                .collect(Collectors.toList());
    }

    public String generateRoomSummary() {
        StringBuilder summary = new StringBuilder("RESUMEN DE HABITACIONES DEL HOTEL\n");
        summary.append("================================\n\n");

        // Resumen por planta
        for (int floor = 1; floor <= 3; floor++) {
            summary.append("Planta ").append(floor).append(":\n");
            summary.append("-----------\n");

            final int currentFloor = floor;
            List<Room> floorRooms = rooms.stream()
                    .filter(room -> room.getRoomNumber() / 100 == currentFloor)
                    .toList();

            for (Room room : floorRooms) {
                summary.append(room.toString()).append("\n");
            }
            summary.append("\n");
        }

        // Estadísticas
        long disponibles = rooms.stream().filter(r -> r.getStatus() == Room.RoomStatus.DISPONIBLE).count();
        long reservadas = rooms.stream().filter(r -> r.getStatus() == Room.RoomStatus.RESERVADA).count();
        long ocupadas = rooms.stream().filter(r -> r.getStatus() == Room.RoomStatus.OCUPADA).count();

        summary.append("Estadísticas:\n");
        summary.append("- Habitaciones disponibles: ").append(disponibles).append("\n");
        summary.append("- Habitaciones reservadas: ").append(reservadas).append("\n");
        summary.append("- Habitaciones ocupadas: ").append(ocupadas).append("\n");

        return summary.toString();
    }
}