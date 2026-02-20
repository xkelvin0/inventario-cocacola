-- ============================================================
-- SCRIPT SQL COMPLETO - Sistema de Gestión de Inventario
-- Base de datos: inventario_db
-- Motor: MySQL
-- ============================================================

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS inventario_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE inventario_db;

-- ============================================================
-- TABLA: producto
-- ============================================================
CREATE TABLE IF NOT EXISTS producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255),
    unidad_medida VARCHAR(20),
    precio_unitario DECIMAL(10,2),
    stock_minimo INT DEFAULT 0,
    estado BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: lote
-- ============================================================
CREATE TABLE IF NOT EXISTS lote (
    id_lote INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT NOT NULL,
    numero_lote VARCHAR(50),
    cantidad_inicial INT NOT NULL DEFAULT 0,
    fecha_vencimiento DATE NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_lote_producto
        FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: inventario
-- ============================================================
CREATE TABLE IF NOT EXISTS inventario (
    id_inventario INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT NOT NULL,
    stock_actual INT NOT NULL DEFAULT 0,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uk_inventario_producto UNIQUE (id_producto),
    CONSTRAINT fk_inventario_producto
        FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: movimiento
-- ============================================================
CREATE TABLE IF NOT EXISTS movimiento (
    id_movimiento INT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('INGRESO', 'SALIDA') NOT NULL,
    fecha DATE NOT NULL,
    cantidad INT NOT NULL,
    id_inventario INT NOT NULL,
    id_lote INT NOT NULL,
    motivo VARCHAR(255),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_movimiento_inventario
        FOREIGN KEY (id_inventario) REFERENCES inventario(id_inventario)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_movimiento_lote
        FOREIGN KEY (id_lote) REFERENCES lote(id_lote)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- DATOS DE EJEMPLO (opcional)
-- ============================================================

-- Producto de ejemplo
INSERT INTO producto (nombre, categoria) VALUES ('Arroz Extra 5kg', 'Abarrotes');
INSERT INTO producto (nombre, categoria) VALUES ('Leche Gloria 1L', 'Lácteos');

-- Inventario asociado (1:1 con producto)
INSERT INTO inventario (stock_actual, id_producto) VALUES (0, 1);
INSERT INTO inventario (stock_actual, id_producto) VALUES (0, 2);

-- Lotes de ejemplo
INSERT INTO lote (fecha_vencimiento, id_producto) VALUES ('2026-12-31', 1);
INSERT INTO lote (fecha_vencimiento, id_producto) VALUES ('2026-06-15', 2);

-- Movimiento de ingreso de ejemplo
INSERT INTO movimiento (tipo, fecha, cantidad, id_inventario, id_lote) VALUES ('INGRESO', '2026-02-19', 100, 1, 1);
UPDATE inventario SET stock_actual = 100 WHERE id_inventario = 1;

INSERT INTO movimiento (tipo, fecha, cantidad, id_inventario, id_lote) VALUES ('INGRESO', '2026-02-19', 50, 2, 2);
UPDATE inventario SET stock_actual = 50 WHERE id_inventario = 2;
