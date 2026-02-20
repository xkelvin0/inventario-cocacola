-- Desactivar chequeo de llaves foráneas para limpieza total
SET FOREIGN_KEY_CHECKS = 0;

-- Limpiar tablas
DELETE FROM movimiento;
DELETE FROM lote;
DELETE FROM inventario;
DELETE FROM producto;

-- Reiniciar autoincrementales
ALTER TABLE producto AUTO_INCREMENT = 1;
ALTER TABLE inventario AUTO_INCREMENT = 1;
ALTER TABLE lote AUTO_INCREMENT = 1;
ALTER TABLE movimiento AUTO_INCREMENT = 1;

-- ============================================================
-- PRODUCTOS COCA-COLA (Estructura Extendida)
-- ============================================================
INSERT INTO producto (nombre, categoria, descripcion, unidad_medida, precio_unitario, stock_minimo) VALUES 
('Coca-Cola Original 500ml', 'Gaseosas', 'Bebida gaseosa original', 'Botella', 2.50, 24),
('Coca-Cola Sin Azúcar 500ml', 'Gaseosas', 'Bebida gaseosa sin azúcar', 'Botella', 2.50, 12),
('Inca Kola Original 500ml', 'Gaseosas', 'Bebida gaseosa sabor nacional', 'Botella', 2.50, 24),
('Sprite Lima-Limón 500ml', 'Gaseosas', 'Bebida gaseosa transparente', 'Botella', 2.50, 12),
('Fanta Naranja 500ml', 'Gaseosas', 'Bebida gaseosa sabor naranja', 'Botella', 2.50, 12),
('Agua San Luis 625ml', 'Aguas', 'Agua mineral sin gas', 'Botella', 1.50, 20),
('Powerade Blue 500ml', 'Isotónicas', 'Bebida para deportistas', 'Botella', 3.50, 10),
('Frugos del Valle Durasno 235ml', 'Jugos', 'Jugo de fruta natural', 'Tetrapack', 1.20, 30);

-- ============================================================
-- INVENTARIO INICIAL (Stock 0)
-- ============================================================
INSERT INTO inventario (id_producto, stock_actual) VALUES (1, 0), (2, 0), (3, 0), (4, 0), (5, 0), (6, 0), (7, 0), (8, 0);

-- ============================================================
-- LOTES INICIALES (Trazabilidad)
-- ============================================================
INSERT INTO lote (id_producto, numero_lote, cantidad_inicial, fecha_vencimiento) VALUES 
(1, 'L-CC001', 100, '2026-12-31'),
(3, 'L-IK001', 100, '2026-11-30');

-- ============================================================
-- MOVIMIENTOS INICIALES (Carga de Apertura)
-- ============================================================
INSERT INTO movimiento (tipo, fecha, cantidad, id_inventario, id_lote, motivo) VALUES 
('INGRESO', CURDATE(), 100, 1, 1, 'Carga inicial de inventario'),
('INGRESO', CURDATE(), 100, 3, 2, 'Carga inicial de inventario');

-- Actualizar stock actual en inventario tras movimientos iniciales
UPDATE inventario SET stock_actual = 100 WHERE id_producto IN (1, 3);

SET FOREIGN_KEY_CHECKS = 1;
