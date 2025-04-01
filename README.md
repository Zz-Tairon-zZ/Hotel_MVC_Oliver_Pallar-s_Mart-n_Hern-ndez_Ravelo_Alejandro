# Sistema de Gestión de Hotel

Este proyecto implementa un sistema de reserva de habitaciones para un hotel utilizando Java y el patrón de diseño MVC (Modelo-Vista-Controlador).

## Descripción

El sistema permite gestionar:
- Habitaciones del hotel (individuales, dobles y suites)
- Clientes
- Reservas

El hotel cuenta con 3 plantas, cada una con 5 habitaciones, para un total de 15 habitaciones.

### Características principales

- Registro y gestión de clientes
- Gestión de habitaciones con diferentes tipos y precios
- Creación, modificación y cancelación de reservas
- Control de check-in y check-out
- Búsqueda y visualización de datos
- Validaciones y restricciones de negocio

## Estructura del proyecto

El proyecto se organiza siguiendo el patrón MVC:

- **Modelo (Model)**: Representa los datos y la lógica de negocio
  - Client: Gestiona la información de los clientes
  - Room: Representa las habitaciones del hotel
  - Reservation: Maneja las reservas

- **Vista (View)**: Interfaz de usuario por consola
  - Main: Punto de entrada del programa y menús de interacción

- **Controlador (Controller)**: Coordina el modelo y la vista
  - ClientController: Gestiona las operaciones relacionadas con clientes
  - RoomController: Gestiona las operaciones relacionadas con habitaciones
  - ReserveController: Gestiona las operaciones relacionadas con reservas

## Instrucciones de ejecución

1. Asegúrate de tener instalado Java Development Kit (JDK) 8 o superior
2. Clona el repositorio:
   ```
   git clone https://github.com/tu-usuario/hotel-management-system.git
   ```
3. Abre el proyecto en tu IDE favorito (Eclipse, IntelliJ IDEA, etc.)
4. Ejecuta la clase `Main.java` ubicada en el paquete `view`

## Ejemplo de uso

### Registrar un cliente
1. En el menú principal, selecciona "2. Gestión de Clientes"
2. Selecciona "2. Registrar nuevo cliente"
3. Ingresa el nombre completo del cliente

### Crear una reserva
1. En el menú principal, selecciona "3. Gestión de Reservas"
2. Selecciona "2. Crear nueva reserva"
3. Selecciona un cliente de la lista mostrada ingresando su ID
4. Selecciona una habitación disponible ingresando su número
5. Ingresa las fechas de check-in y check-out en formato dd/MM/yyyy

### Consultar habitaciones disponibles
1. En el menú principal, selecciona "1. Gestión de Habitaciones"
2. Selecciona "4. Buscar habitaciones por estado"
3. Selecciona "1. DISPONIBLE"

## Restricciones implementadas

- Un cliente puede tener máximo 3 reservas activas simultáneamente
- El máximo de días para una reserva es de 90 días
- No se permiten reservas en fechas pasadas
- El check-out debe ser posterior al check-in
- Una habitación solo puede tener una reserva activa en un rango de fechas
- Si se cancela una reserva, la habitación vuelve a estar disponible
- Una reserva solo puede ser cancelada si la fecha de check-in aún no ha comenzado

## Creadores del proyecto:
  - Alejandro Hernández Ravelo
  - Martín Oliver Pallarés
