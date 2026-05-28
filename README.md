# Mi Proyecto Aucorsa

Aplicación de escritorio desarrollada en Java que permite gestionar información relacionada con autobuses, conductores y rutas de Aucorsa mediante una interfaz gráfica basada en Swing.

## Tecnologías utilizadas

- Java 17
- Swing
- Maven
- MySQL
- Git

# Funcionalidades principales

- Gestión de autobuses
- Gestión de conductores
- Gestión de rutas
- Visualización de detalles
- Conexión a base de datos MySQL

## Requisitos

* MySQL WorkBench 8.0 CE
* Un Entorno de Desarrollo ya sea Eclipse o IntelliJ
* XAMMP Control Panel
* JDK 17 o superior
* Git

## Instalación y ejecución

# Base de datos

La aplicación utiliza MySQL para almacenar la información.

## Tablas principales

- buses
- conductores
- rutas
- lugares

### 1. Clonar repositorio

```bash
git clone https://github.com/usuario/aucorsa.git
```

### 2. Abrir proyecto en IntelliJ o Eclipse

Importar el proyecto como proyecto Maven.

### 3. Configurar la base de datos

Crear una base de datos llamada:

```sql
aucorsa
```

Ejecutar estos comandos para insertar los datos a la base de datos:

```plaintext
-- Tabla Bus
CREATE TABLE Bus (
    register VARCHAR(10) PRIMARY KEY,
    type VARCHAR(20),
    license VARCHAR(20)
);

-- Tabla Driver
CREATE TABLE Driver (
    numdriver INT PRIMARY KEY,
    name VARCHAR(50),
    surname VARCHAR(50)
);

-- Tabla Place
CREATE TABLE Place (
    idplace INT PRIMARY KEY,
    cp VARCHAR(10),
    city VARCHAR(50),
    site VARCHAR(50)
);

-- Tabla intermedia B-D-P (relación ternaria)
CREATE TABLE BDP (
    register VARCHAR(10),
    numdriver INT,
    idplace INT,
    day_of_week VARCHAR(15),
    PRIMARY KEY (register, numdriver, idplace),
    FOREIGN KEY (register) REFERENCES Bus(register),
    FOREIGN KEY (numdriver) REFERENCES Driver(numdriver),
    FOREIGN KEY (idplace) REFERENCES Place(idplace)
);

-- Insertamos buses
INSERT INTO Bus VALUES 
('B001', 'Urbano', 'LIC001'),
('B002', 'Interurbano', 'LIC002'),
('B003', 'Turismo', 'LIC003'),
('B004', 'Escolar', 'LIC004'),
('B005', 'Urbano', 'LIC005'),
('B006', 'Turismo', 'LIC006'),
('B007', 'Interurbano', 'LIC007'),
('B008', 'Urbano', 'LIC008'),
('B009', 'Escolar', 'LIC009'),
('B010', 'Turismo', 'LIC010');

-- Insertamos conductores
INSERT INTO Driver VALUES 
(101, 'Carlos', 'García'),
(102, 'Lucía', 'Pérez'),
(103, 'Manuel', 'Martín'),
(104, 'Laura', 'López'),
(105, 'Javier', 'Sánchez'),
(106, 'Marta', 'Fernández'),
(107, 'David', 'Ruiz'),
(108, 'Ana', 'Díaz'),
(109, 'Pablo', 'Gómez'),
(110, 'Elena', 'Navarro');

-- Insertamos lugares
INSERT INTO Place VALUES 
(1, '14001', 'Córdoba', 'Centro'),
(2, '28013', 'Madrid', 'Sol'),
(3, '41001', 'Sevilla', 'Triana'),
(4, '08001', 'Barcelona', 'Gótico'),
(5, '46001', 'Valencia', 'Carmen'),
(6, '29001', 'Málaga', 'Soho'),
(7, '03001', 'Alicante', 'Explanada'),
(8, '35001', 'Las Palmas', 'Vegueta'),
(9, '07001', 'Palma', 'Catedral'),
(10, '15001', 'A Coruña', 'Marina');

-- Insertamos datos en la relación BDP
INSERT INTO BDP VALUES 
('B001', 101, 1, 'Monday'),
('B002', 102, 2, 'Tuesday'),
('B003', 103, 3, 'Wednesday'),
('B004', 104, 4, 'Thursday'),
('B005', 105, 5, 'Friday'),
('B006', 106, 6, 'Monday'),
('B007', 107, 7, 'Tuesday'),
('B008', 108, 8, 'Wednesday'),
('B009', 109, 9, 'Thursday'),
('B010', 110, 10, 'Friday');

-- CONSULTAS ÚTILES

-- 1. Mostrar qué conductor conduce qué bus a qué lugar en qué día
SELECT 
    d.name || ' ' || d.surname AS driver_name,
    b.register AS bus,
    p.city || ' - ' || p.site AS destination,
    bd.day_of_week
FROM BDP bd
JOIN Driver d ON bd.numdriver = d.numdriver
JOIN Bus b ON bd.register = b.register
JOIN Place p ON bd.idplace = p.idplace;

-- 2. Mostrar todos los buses con su tipo y licencia
SELECT * FROM Bus;

-- 3. Mostrar todos los conductores que van a Sevilla
SELECT DISTINCT d.name, d.surname
FROM BDP bd
JOIN Driver d ON bd.numdriver = d.numdriver
JOIN Place p ON bd.idplace = p.idplace
WHERE p.city = 'Sevilla';

-- 4. Contar cuántos viajes se realizan cada día de la semana
SELECT day_of_week, COUNT(*) AS num_viajes
FROM BDP
GROUP BY day_of_week;

-- 5. Listar los lugares que visitan más de un bus
SELECT p.city, p.site, COUNT(DISTINCT bd.register) AS num_buses
FROM BDP bd
JOIN Place p ON bd.idplace = p.idplace
GROUP BY p.city, p.site
HAVING COUNT(DISTINCT bd.register) > 1;
```

### 4. Configurar conexión MySQL

Editar los parámetros de conexión en:

```plaintext
controller/connection/ConexionBBDD.java
```

Modificar según los parámetros que tengas:

```java
String url = "jdbc:mysql://localhost:3306/aucorsa";
String usuario = "root";
String password = "";
```

### 5. Ejecutar aplicación

Ejecutar la clase:

```plaintext
Main.java
```

# Arquitectura del Proyecto

La aplicación sigue una arquitectura MVC (Model View Controller) para separar responsabilidades y facilitar el mantenimiento.

## Estructura de carpetas

| Carpeta | Función |
|---|---|
| app | Punto de entrada de la aplicación |
| controller | Controladores y lógica de interacción |
| models | Entidades y lógica de datos |
| view | Interfaces gráficas Swing |
| utils | Métodos auxiliares |
| resources | Recursos estáticos |
| test | Pruebas unitarias |

## Explicación MVC

### Model

Gestiona los datos y entidades de negocio:

- Bus
- Conductor
- Lugar

### View

Gestiona todas las ventanas y componentes gráficos.

### Controller

Gestiona eventos, acciones del usuario y comunicación entre vista y modelo.

# Diagrama UML

El siguiente diagrama representa la arquitectura principal del sistema.

![Diagrama UML](docs/dada.png)
