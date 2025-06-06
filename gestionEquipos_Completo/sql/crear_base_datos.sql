-- Database for inventory and maintenance
DROP DATABASE IF EXISTS gestion_equipos;
CREATE DATABASE gestion_equipos CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE gestion_equipos;

CREATE TABLE equipos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  serial VARCHAR(100) UNIQUE NOT NULL,
  tipo ENUM('PC','TV','VideoBeam','Impresora') NOT NULL,
  marca VARCHAR(100),
  modelo VARCHAR(100),
  procesador VARCHAR(100),
  anio_compra INT NOT NULL,
  estado VARCHAR(50),
  observaciones TEXT
);

CREATE TABLE mantenimientos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_equipo INT NOT NULL,
  tipo_mantenimiento ENUM('Preventivo','Correctivo') NOT NULL,
  descripcion TEXT,
  fecha_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id_equipo) REFERENCES equipos(id) ON DELETE CASCADE
);

INSERT INTO equipos (serial,tipo,marca,modelo,procesador,anio_compra,estado,observaciones) VALUES
('SN1001','PC','Dell','OptiPlex','Intel i7',2022,'Activo','Compacto'),
('SN1002','TV','Samsung','UE55MU','No aplica',2021,'Activo','Smart TV');

INSERT INTO mantenimientos (id_equipo,tipo_mantenimiento,descripcion) VALUES
(1,'Preventivo','Limpieza interna'),
(2,'Correctivo','Reemplazo de fuente');
