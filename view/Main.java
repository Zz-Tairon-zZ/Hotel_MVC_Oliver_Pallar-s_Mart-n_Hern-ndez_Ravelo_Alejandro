package view;

import controller.ClientController;
import controller.ReserveController;
import controller.RoomController;
import model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final RoomController roomController = new RoomController();
    private static final ClientController clientController = new ClientController();
    private static final ReserveController reserveController = new ReserveController(roomController, clientController);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        System.out.println("SISTEMA DE GESTIÓN DE HOTEL");
        System.out.println("===========================");

        // Crear algunos clientes de ejemplo
        clientController.createClient("Juan Pérez");
        clientController.createClient("María López");

        boolean running = true;

        while (running) {
            displayMainMenu();
            System.out.println("Seleccione una opción: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    roomManagementMenu();
                    break;
                case 2:
                    clientManagementMenu();
                    break;
                case 3:
                    reservationManagementMenu();
                    break;
                case 4:
                    running = false;
                    System.out.println("¡Gracias por usar el sistema de gestión de hotel!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }

        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\nMENU PRINCIPAL");
        System.out.println("1. Gestión de Habitaciones");
        System.out.println("2. Gestión de Clientes");
        System.out.println("3. Gestión de Reservas");
        System.out.println("4. Salir");
    }

    // Gestión de Habitaciones
    private static void roomManagementMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\nGESTIÓN DE HABITACIONES");
            System.out.println("1. Ver todas las habitaciones");
            System.out.println("2. Buscar habitación por número");
            System.out.println("3. Buscar habitaciones por tipo");
            System.out.println("4. Buscar habitaciones por estado");
            System.out.println("5. Ver resumen de habitaciones");
            System.out.println("6. Volver al menú principal");
            System.out.println("\n");
            System.out.println("Seleccione una opción: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    displayAllRooms();
                    break;
                case 2:
                    searchRoomByNumber();
                    break;
                case 3:
                    searchRoomsByType();
                    break;
                case 4:
                    searchRoomsByStatus();
                    break;
                case 5:
                    displayRoomSummary();
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void displayAllRooms() {
        System.out.println("\nTODAS LAS HABITACIONES");
        List<Room> rooms = roomController.getAllRooms();
        rooms.forEach(System.out::println);
    }

    private static void searchRoomByNumber() {
        int roomNumber = readIntegerInput("Ingrese el número de habitación: ");
        Room room = roomController.getRoomByNumber(roomNumber);

        if (room != null) {
            System.out.println("\nDetalles de la habitación:");
            System.out.println(room);
        } else {
            System.out.println("Habitación no encontrada.");
        }
    }

    private static void searchRoomsByType() {
        System.out.println("\nTIPOS DE HABITACIÓN");
        System.out.println("1. INDIVIDUAL");
        System.out.println("2. DOBLE");
        System.out.println("3. SUITE");

        int option = readIntegerInput("Seleccione un tipo: ");
        Room.RoomType type;

        switch (option) {
            case 1:
                type = Room.RoomType.INDIVIDUAL;
                break;
            case 2:
                type = Room.RoomType.DOBLE;
                break;
            case 3:
                type = Room.RoomType.SUITE;
                break;
            default:
                System.out.println("Opción no válida.");
                return;
        }

        List<Room> rooms = roomController.getRoomsByType(type);

        if (rooms.isEmpty()) {
            System.out.println("No hay habitaciones de este tipo.");
        } else {
            System.out.println("\nHABITACIONES DE TIPO " + type);
            rooms.forEach(System.out::println);
        }
    }

    private static void searchRoomsByStatus() {
        System.out.println("\nESTADOS DE HABITACIÓN");
        System.out.println("1. DISPONIBLE");
        System.out.println("2. RESERVADA");
        System.out.println("3. OCUPADA");

        int option = readIntegerInput("Seleccione un estado: ");
        Room.RoomStatus status;

        switch (option) {
            case 1:
                status = Room.RoomStatus.DISPONIBLE;
                break;
            case 2:
                status = Room.RoomStatus.RESERVADA;
                break;
            case 3:
                status = Room.RoomStatus.OCUPADA;
                break;
            default:
                System.out.println("Opción no válida.");
                return;
        }

        List<Room> rooms = roomController.getRoomsByStatus(status);

        if (rooms.isEmpty()) {
            System.out.println("No hay habitaciones en este estado.");
        } else {
            System.out.println("\nHABITACIONES EN ESTADO " + status);
            rooms.forEach(System.out::println);
        }
    }

    private static void displayRoomSummary() {
        System.out.println("\n" + roomController.generateRoomSummary());
    }

    // Gestión de Clientes
    private static void clientManagementMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\nGESTIÓN DE CLIENTES");
            System.out.println("1. Ver todos los clientes");
            System.out.println("2. Registrar nuevo cliente");
            System.out.println("3. Buscar cliente por ID");
            System.out.println("4. Buscar cliente por nombre");
            System.out.println("5. Ver reservas activas de cliente");
            System.out.println("6. Ver historial de reservas de cliente");
            System.out.println("7. Ver resumen de clientes");
            System.out.println("8. Volver al menú principal");

            int option = readIntegerInput("Seleccione una opción: ");

            switch (option) {
                case 1:
                    displayAllClients();
                    break;
                case 2:
                    registerNewClient();
                    break;
                case 3:
                    searchClientById();
                    break;
                case 4:
                    searchClientByName();
                    break;
                case 5:
                    viewClientActiveReservations();
                    break;
                case 6:
                    viewClientReservationHistory();
                    break;
                case 7:
                    displayClientSummary();
                    break;
                case 8:
                    back = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void displayAllClients() {
        System.out.println("\nTODOS LOS CLIENTES");
        List<Client> clients = clientController.getAllClients();

        if (clients.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            clients.forEach(System.out::println);
        }
    }

    private static void registerNewClient() {
        System.out.println("\nREGISTRO DE NUEVO CLIENTE");
        System.out.print("Ingrese el nombre completo: ");
        String fullName = scanner.nextLine();

        try {
            Client client = clientController.createClient(fullName);
            System.out.println("Cliente registrado con éxito. ID: " + client.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void searchClientById() {
        System.out.println("\nBÚSQUEDA DE CLIENTE POR ID");
        System.out.print("Ingrese el ID del cliente: ");
        String id = scanner.nextLine();

        Client client = clientController.getClientById(id);
        if (client != null) {
            System.out.println("Cliente encontrado:");
            System.out.println(client);
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void searchClientByName() {
        System.out.println("\nBÚSQUEDA DE CLIENTE POR NOMBRE");
        System.out.print("Ingrese el nombre del cliente: ");
        String name = scanner.nextLine();

        Client client = clientController.getClientByName(name);
        if (client != null) {
            System.out.println("Cliente encontrado:");
            System.out.println(client);
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void viewClientActiveReservations() {
        System.out.println("\nRESERVAS ACTIVAS DE CLIENTE");
        System.out.print("Ingrese el ID del cliente: ");
        String id = scanner.nextLine();

        List<Reservation> reservations = clientController.getClientActiveReservations(id);
        if (reservations.isEmpty()) {
            System.out.println("El cliente no tiene reservas activas o no existe.");
        } else {
            System.out.println("Reservas activas del cliente:");
            reservations.forEach(reservation -> {
                System.out.println("-------------------------------");
                System.out.println(reservation);
            });
        }
    }

    private static void viewClientReservationHistory() {
        System.out.println("\nHISTORIAL DE RESERVAS DE CLIENTE");
        System.out.print("Ingrese el ID del cliente: ");
        String id = scanner.nextLine();

        List<Reservation> reservations = clientController.getClientReservationHistory(id);
        if (reservations.isEmpty()) {
            System.out.println("El cliente no tiene historial de reservas o no existe.");
        } else {
            System.out.println("Historial de reservas del cliente:");
            reservations.forEach(reservation -> {
                System.out.println("-------------------------------");
                System.out.println(reservation);
            });
        }
    }

    private static void displayClientSummary() {
        System.out.println("\n" + clientController.generateClientSummary());
    }

    // Gestión de Reservas
    private static void reservationManagementMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\nGESTIÓN DE RESERVAS");
            System.out.println("1. Ver todas las reservas");
            System.out.println("2. Crear nueva reserva");
            System.out.println("3. Cancelar reserva");
            System.out.println("4. Buscar reserva por ID");
            System.out.println("5. Realizar check-in");
            System.out.println("6. Realizar check-out");
            System.out.println("7. Volver al menú principal");

            int option = readIntegerInput("Seleccione una opción: ");

            switch (option) {
                case 1:
                    displayAllReservations();
                    break;
                case 2:
                    createNewReservation();
                    break;
                case 3:
                    cancelReservation();
                    break;
                case 4:
                    searchReservationById();
                    break;
                case 5:
                    performCheckIn();
                    break;
                case 6:
                    performCheckOut();
                    break;
                case 7:
                    back = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void displayAllReservations() {
        System.out.println("\nTODAS LAS RESERVAS");
        List<Reservation> reservations = reserveController.getAllReservations();

        if (reservations.isEmpty()) {
            System.out.println("No hay reservas registradas.");
        } else {
            reservations.forEach(reservation -> {
                System.out.println("-------------------------------");
                System.out.println(reservation);
            });
        }
    }

    private static void createNewReservation() {
        System.out.println("\nCREACIÓN DE NUEVA RESERVA");

        // Mostrar lista de clientes
        System.out.println("Clientes disponibles:");
        List<Client> clients = clientController.getAllClients();
        if (clients.isEmpty()) {
            System.out.println("No hay clientes registrados. Registre un cliente primero.");
            return;
        }

        for (Client client : clients) {
            System.out.println(client);
        }

        // Seleccionar cliente
        System.out.print("Ingrese el ID del cliente: ");
        String clientId = scanner.nextLine();

        if (!clientController.canClientMakeReservation(clientId)) {
            System.out.println("El cliente ya tiene el máximo de reservas activas o no existe.");
            return;
        }

        // Mostrar habitaciones disponibles
        System.out.println("\nHabitaciones disponibles:");
        List<Room> availableRooms = roomController.getRoomsByStatus(Room.RoomStatus.DISPONIBLE);

        if (availableRooms.isEmpty()) {
            System.out.println("No hay habitaciones disponibles.");
            return;
        }

        for (Room room : availableRooms) {
            System.out.println(room);
        }

        // Seleccionar habitación
        int roomNumber = readIntegerInput("Ingrese el número de habitación: ");

        // Ingresar fechas
        LocalDate checkInDate = readDateInput("Ingrese fecha de check-in (dd/MM/yyyy): ");
        LocalDate checkOutDate = readDateInput("Ingrese fecha de check-out (dd/MM/yyyy): ");

        // Crear reserva
        try {
            Reservation reservation = reserveController.createReservation(clientId, roomNumber, checkInDate, checkOutDate);
            System.out.println("Reserva creada con éxito:");
            System.out.println(reservation);
        } catch (Exception e) {
            System.out.println("Error al crear la reserva: " + e.getMessage());
        }
    }

    private static void cancelReservation() {
        System.out.println("\nCANCELACIÓN DE RESERVA");
        System.out.print("Ingrese el ID de la reserva: ");
        String reservationId = scanner.nextLine();

        try {
            reserveController.cancelReservation(reservationId);
            System.out.println("Reserva cancelada con éxito.");
        } catch (Exception e) {
            System.out.println("Error al cancelar la reserva: " + e.getMessage());
        }
    }

    private static void searchReservationById() {
        System.out.println("\nBÚSQUEDA DE RESERVA POR ID");
        System.out.print("Ingrese el ID de la reserva: ");
        String reservationId = scanner.nextLine();

        Reservation reservation = reserveController.getReservationById(reservationId);
        if (reservation != null) {
            System.out.println("Reserva encontrada:");
            System.out.println(reservation);
        } else {
            System.out.println("Reserva no encontrada.");
        }
    }

    private static void performCheckIn() {
        System.out.println("\nREALIZAR CHECK-IN");
        System.out.print("Ingrese el ID de la reserva: ");
        String reservationId = scanner.nextLine();

        try {
            reserveController.checkIn(reservationId);
            System.out.println("Check-in realizado con éxito.");
        } catch (Exception e) {
            System.out.println("Error al realizar el check-in: " + e.getMessage());
        }
    }

    private static void performCheckOut() {
        System.out.println("\nREALIZAR CHECK-OUT");
        System.out.print("Ingrese el ID de la reserva: ");
        String reservationId = scanner.nextLine();

        try {
            reserveController.checkOut(reservationId);
            System.out.println("Check-out realizado con éxito.");
        } catch (Exception e) {
            System.out.println("Error al realizar el check-out: " + e.getMessage());
        }
    }

    // Utilidades
    private static int readIntegerInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }

    private static LocalDate readDateInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String dateStr = scanner.nextLine();

            try {
                return LocalDate.parse(dateStr, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha incorrecto. Use dd/MM/yyyy.");
            }
        }
    }
}