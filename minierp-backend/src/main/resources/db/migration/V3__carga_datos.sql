
INSERT INTO usuarios (user_name, password, rol, name, last_name) VALUES 
('admin_user', '$2a$10$zMHYBqmfLXBkh9HvzTZ3i.1xdbzkZ.nCmPGC.GQ5X/OFTR9ivjdPC', 'ADMINISTRACION', 'Juan', 'Pérez'),
('compras_user', '$2a$10$V6J7dcAYtg9uI1ley7zUmOoo16PDB7xTwrBN0OYXCZAcAzJK/3P5m', 'COMPRAS', 'María', 'López'),
('inv_user', '$2a$10$0CkS9xmIMuHHZUmEgW0JeuloRm75Kuaks6tOnwx.rftaGfbWpMH9G', 'INVENTARIO', 'Carlos', 'Gómez'),
('ventas_user', '$2a$10$Lj6LuRbm2UrwjJ1VSAUCKuCROt/BTuK11./TjiRwqD5fAv4X8tvnu', 'VENTAS', 'Ana', 'Martínez');

INSERT INTO proveedores (id, nit, nombre_proveedor, telefono, direccion, activo) VALUES 
(1, '111111-1', 'Distribuidora Tecno S.A.', '2222-1111', 'Zona 1, Ciudad', true),
(2, '222222-2', 'Importadora Global IT', '2222-2222', 'Zona 4, Ciudad', true),
(3, '333333-3', 'ElectroGuate', '2222-3333', 'Zona 9, Ciudad', true),
(4, '444444-4', 'PC Express Mayoristas', '2222-4444', 'Zona 10, Ciudad', true),
(5, '555555-5', 'MegaComponentes', '2222-5555', 'Zona 11, Ciudad', true),
(6, '666666-6', 'TechSupply Corp', '2222-6666', 'Zona 12, Ciudad', true),
(7, '777777-7', 'Inversiones Informáticas', '2222-7777', 'Quetzaltenango', true),
(8, '888888-8', 'Redes y Periféricos S.A.', '2222-8888', 'Antigua Guatemala', true);

INSERT INTO clientes (id, nit, nombre, apellido, correo, direccion, activo) VALUES 
(1, 'CF', 'Consumidor', 'Final', 'cf@erp.com', 'Ciudad', true),
(2, '901234-1', 'Juan', 'Pérez', 'juan@mail.com', 'Zona 1', true),
(3, '901234-2', 'María', 'López', 'maria@mail.com', 'Zona 2', true),
(4, '901234-3', 'Carlos', 'García', 'carlos@mail.com', 'Zona 3', true),
(5, '901234-4', 'Ana', 'Martínez', 'ana@mail.com', 'Zona 4', true),
(6, '901234-5', 'Luis', 'Rodríguez', 'luis@mail.com', 'Zona 5', true),
(7, '901234-6', 'Laura', 'Hernández', 'laura@mail.com', 'Zona 6', true),
(8, '901234-7', 'Jorge', 'Gómez', 'jorge@mail.com', 'Zona 7', true),
(9, '901234-8', 'Sofía', 'Díaz', 'sofia@mail.com', 'Zona 8', true),
(10, '901234-9', 'Diego', 'Pineda', 'diego@mail.com', 'Zona 9', true),
(11, '901235-0', 'Lucía', 'Morales', 'lucia@mail.com', 'Zona 10', true),
(12, '901235-1', 'Miguel', 'Ríos', 'miguel@mail.com', 'Zona 11', true),
(13, '901235-2', 'Elena', 'Castro', 'elena@mail.com', 'Zona 12', true),
(14, '901235-3', 'Pablo', 'Ortiz', 'pablo@mail.com', 'Zona 13', true),
(15, '901235-4', 'Carmen', 'Ruiz', 'carmen@mail.com', 'Zona 14', true),
(16, '901235-5', 'Andrés', 'Méndez', 'andres@mail.com', 'Zona 15', true),
(17, '901235-6', 'Paula', 'Vargas', 'paula@mail.com', 'Zona 16', true),
(18, '901235-7', 'Hugo', 'Reyes', 'hugo@mail.com', 'Zona 17', true),
(19, '901235-8', 'Daniela', 'Cruz', 'daniela@mail.com', 'Zona 18', true),
(20, '901235-9', 'Fernando', 'Herrera', 'fernando@mail.com', 'Zona 21', true);

INSERT INTO categorias (id, nombre, descripcion) VALUES 
(1, 'Laptops', 'Equipos portátiles'),
(2, 'Monitores', 'Pantallas de visualización'),
(3, 'Periféricos', 'Teclados, ratones y accesorios'),
(4, 'Almacenamiento', 'Discos duros y SSD'),
(5, 'Redes', 'Routers y switches');

INSERT INTO productos (id, codigo, nombre, precio_venta, categoria_id, activo) VALUES 
(1, 'LAP-001', 'Laptop Dell Inspiron 15', 5500.00, 1, true),
(2, 'LAP-002', 'Laptop HP Pavilion', 6000.00, 1, true),
(3, 'LAP-003', 'Lenovo ThinkPad E14', 7200.00, 1, true),
(4, 'LAP-004', 'Asus ROG Strix G15', 12500.00, 1, true),
(5, 'LAP-005', 'Acer Nitro 5', 8500.00, 1, true),
(6, 'LAP-006', 'MacBook Air M1', 9500.00, 1, true),
(7, 'MON-001', 'Monitor LG 24 Pulgadas', 1200.00, 2, true),
(8, 'MON-002', 'Monitor Samsung Curvo 27"', 1800.00, 2, true),
(9, 'MON-003', 'Monitor Dell UltraSharp 24"', 2200.00, 2, true),
(10, 'MON-004', 'Monitor AOC Gaming 144Hz', 2100.00, 2, true),
(11, 'MON-005', 'Monitor BenQ 22 Pulgadas', 900.00, 2, true),
(12, 'MON-006', 'Monitor Asus ProArt', 3500.00, 2, true),
(13, 'PER-001', 'Teclado Mecánico Redragon', 350.00, 3, true),
(14, 'PER-002', 'Mouse Logitech G203', 250.00, 3, true),
(15, 'PER-003', 'Teclado Inalámbrico Microsoft', 400.00, 3, true),
(16, 'PER-004', 'Mouse Inalámbrico HP', 150.00, 3, true),
(17, 'PER-005', 'Audífonos HyperX Cloud', 700.00, 3, true),
(18, 'PER-006', 'Webcam Logitech C920', 650.00, 3, true),
(19, 'ALM-001', 'SSD Kingston 480GB', 350.00, 4, true),
(20, 'ALM-002', 'SSD Samsung 970 EVO 1TB', 1200.00, 4, true),
(21, 'ALM-003', 'Disco Duro Externo WD 2TB', 750.00, 4, true),
(22, 'ALM-004', 'Disco Duro Seagate 1TB', 400.00, 4, true),
(23, 'ALM-005', 'SSD Adata 240GB', 220.00, 4, true),
(24, 'ALM-006', 'Memoria USB SanDisk 64GB', 85.00, 4, true),
(25, 'RED-001', 'Router TP-Link Archer', 450.00, 5, true),
(26, 'RED-002', 'Switch Mercusys 8 Puertos', 120.00, 5, true),
(27, 'RED-003', 'Router Asus RT-AX58U', 1500.00, 5, true),
(28, 'RED-004', 'Repetidor Xiaomi Mi WiFi', 150.00, 5, true),
(29, 'RED-005', 'Cable UTP Cat6 30m', 100.00, 5, true),
(30, 'RED-006', 'Adaptador WiFi USB TP-Link', 95.00, 5, true);

INSERT INTO compras (id, proveedor_id, usuario_id, fecha_compra, total) VALUES 
(1, 1, 3, '2026-09-01', 25000.00), (2, 2, 3, '2026-09-02', 15000.00), (3, 3, 3, '2026-09-03', 8000.00),
(4, 4, 3, '2026-09-04', 45000.00), (5, 5, 3, '2026-09-05', 12000.00), (6, 6, 3, '2026-09-06', 9000.00),
(7, 7, 3, '2026-09-07', 3500.00),  (8, 8, 3, '2026-09-08', 4000.00),  (9, 1, 3, '2026-09-09', 11000.00),
(10, 2, 3, '2026-09-10', 22000.00), (11, 3, 3, '2026-09-11', 13000.00), (12, 4, 3, '2026-09-12', 27000.00),
(13, 5, 3, '2026-09-13', 6000.00),  (14, 6, 3, '2026-09-14', 8500.00),  (15, 7, 3, '2026-09-15', 5000.00);

INSERT INTO lotes (id, producto_id, compra_id, codigo_lote, cantidad_inicial, cantidad_actual, costo_unitario, fecha_ingreso, activo) VALUES 
(1, 1, 1, 'L-01', 50, 48, 4500.00, '2026-09-01', true), (2, 2, 2, 'L-02', 50, 48, 5000.00, '2026-09-02', true),
(3, 3, 3, 'L-03', 50, 48, 6000.00, '2026-09-03', true), (4, 4, 4, 'L-04', 50, 48, 10000.00, '2026-09-04', true),
(5, 5, 5, 'L-05', 50, 48, 7000.00, '2026-09-05', true), (6, 6, 6, 'L-06', 50, 47, 8000.00, '2026-09-06', true),
(7, 7, 7, 'L-07', 50, 47, 900.00, '2026-09-07', true),  (8, 8, 8, 'L-08', 50, 49, 1400.00, '2026-09-08', true),
(9, 9, 9, 'L-09', 50, 49, 1800.00, '2026-09-09', true), (10, 13, 10, 'L-10', 50, 49, 200.00, '2026-09-10', true),
(11, 14, 11, 'L-11', 50, 48, 150.00, '2026-09-11', true), (12, 19, 12, 'L-12', 50, 47, 200.00, '2026-09-12', true),
(13, 20, 13, 'L-13', 50, 48, 900.00, '2026-09-13', true), (14, 25, 14, 'L-14', 50, 48, 300.00, '2026-09-14', true),
(15, 26, 15, 'L-15', 50, 49, 80.00, '2026-09-15', true);

INSERT INTO detalle_compras (id, compra_id, producto_id, cantidad, costo_unitario, subtotal) VALUES 
(1, 1, 1, 50, 4500.00, 225000.00), (2, 2, 2, 50, 5000.00, 250000.00), (3, 3, 3, 50, 6000.00, 300000.00),
(4, 4, 4, 50, 10000.00, 500000.00), (5, 5, 5, 50, 7000.00, 350000.00), (6, 6, 6, 50, 8000.00, 400000.00),
(7, 7, 7, 50, 900.00, 45000.00),   (8, 8, 8, 50, 1400.00, 70000.00),  (9, 9, 9, 50, 1800.00, 90000.00),
(10, 10, 13, 50, 200.00, 10000.00), (11, 11, 14, 50, 150.00, 7500.00), (12, 12, 19, 50, 200.00, 10000.00),
(13, 13, 20, 50, 900.00, 45000.00), (14, 14, 25, 50, 300.00, 15000.00), (15, 15, 26, 50, 80.00, 4000.00);

INSERT INTO ventas (id, cliente_id, usuario_id, subtotal, iva, total) VALUES 
(1, 1, 2, 5500.00, 660.00, 6160.00), (2, 2, 2, 6000.00, 720.00, 6720.00), (3, 3, 2, 7200.00, 864.00, 8064.00),
(4, 4, 2, 12500.00, 1500.00, 14000.00), (5, 5, 2, 8500.00, 1020.00, 9520.00), (6, 6, 2, 9500.00, 1140.00, 10640.00),
(7, 7, 2, 1200.00, 144.00, 1344.00), (8, 8, 2, 1800.00, 216.00, 2016.00), (9, 9, 2, 2200.00, 264.00, 2464.00),
(10, 10, 2, 350.00, 42.00, 392.00), (11, 11, 2, 250.00, 30.00, 280.00), (12, 12, 2, 350.00, 42.00, 392.00),
(13, 13, 2, 1200.00, 144.00, 1344.00), (14, 14, 2, 450.00, 54.00, 504.00), (15, 15, 2, 120.00, 14.40, 134.40),
(16, 16, 2, 5500.00, 660.00, 6160.00), (17, 17, 2, 6000.00, 720.00, 6720.00), (18, 18, 2, 7200.00, 864.00, 8064.00),
(19, 19, 2, 12500.00, 1500.00, 14000.00), (20, 20, 2, 8500.00, 1020.00, 9520.00), (21, 1, 2, 9500.00, 1140.00, 10640.00),
(22, 2, 2, 1200.00, 144.00, 1344.00), (23, 3, 2, 9500.00, 1140.00, 10640.00), (24, 4, 2, 350.00, 42.00, 392.00),
(25, 5, 2, 250.00, 30.00, 280.00);

INSERT INTO detalle_ventas (id, venta_id, producto_id, cantidad, precio_unitario, subtotal) VALUES 
(1, 1, 1, 1, 5500.00, 5500.00), (2, 2, 2, 1, 6000.00, 6000.00), (3, 3, 3, 1, 7200.00, 7200.00),
(4, 4, 4, 1, 12500.00, 12500.00), (5, 5, 5, 1, 8500.00, 8500.00), (6, 6, 6, 1, 9500.00, 9500.00),
(7, 7, 7, 1, 1200.00, 1200.00), (8, 8, 8, 1, 1800.00, 1800.00), (9, 9, 9, 1, 2200.00, 2200.00),
(10, 10, 13, 1, 350.00, 350.00), (11, 11, 14, 1, 250.00, 250.00), (12, 12, 19, 1, 350.00, 350.00),
(13, 13, 20, 1, 1200.00, 1200.00), (14, 14, 25, 1, 450.00, 450.00), (15, 15, 26, 1, 120.00, 120.00),
(16, 16, 1, 1, 5500.00, 5500.00), (17, 17, 2, 1, 6000.00, 6000.00), (18, 18, 3, 1, 7200.00, 7200.00),
(19, 19, 4, 1, 12500.00, 12500.00), (20, 20, 5, 1, 8500.00, 8500.00), (21, 21, 6, 1, 9500.00, 9500.00),
(22, 22, 7, 1, 1200.00, 1200.00), (23, 23, 6, 1, 9500.00, 9500.00), (24, 24, 19, 1, 350.00, 350.00),
(25, 25, 14, 1, 250.00, 250.00);

SELECT setval('usuarios_id_seq', (SELECT MAX(id) FROM usuarios));
SELECT setval('clientes_id_seq', (SELECT MAX(id) FROM clientes));
SELECT setval('proveedores_id_seq', (SELECT MAX(id) FROM proveedores));
SELECT setval('categorias_id_seq', (SELECT MAX(id) FROM categorias));
SELECT setval('productos_id_seq', (SELECT MAX(id) FROM productos));
SELECT setval('compras_id_seq', (SELECT MAX(id) FROM compras));
SELECT setval('ventas_id_seq', (SELECT MAX(id) FROM ventas));
SELECT setval('lotes_id_seq', (SELECT MAX(id) FROM lotes));