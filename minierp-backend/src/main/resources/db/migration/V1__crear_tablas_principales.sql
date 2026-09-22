CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    rol VARCHAR(50) NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);
 
CREATE TABLE clientes (
    id SERIAL PRIMARY KEY,
    nit VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    direccion VARCHAR(255),
    correo VARCHAR(100),
    activo BOOLEAN DEFAULT TRUE
);
 
CREATE TABLE proveedores (
    id SERIAL PRIMARY KEY,
    nit VARCHAR(20) UNIQUE NOT NULL,
    nombre_proveedor VARCHAR(150) NOT NULL,
    telefono VARCHAR(20),
    direccion VARCHAR(255),
    activo BOOLEAN DEFAULT TRUE
);
 
CREATE TABLE categorias (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    descripcion TEXT
);
 
CREATE TABLE productos (
    id SERIAL PRIMARY KEY,
    codigo VARCHAR(50) UNIQUE NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    precio_venta DECIMAL(10, 2) NOT NULL,
    imagen VARCHAR(255),
    categoria_id INTEGER REFERENCES categorias(id),
    activo BOOLEAN DEFAULT TRUE
);
 
CREATE TABLE compras (
    id SERIAL PRIMARY KEY,
    proveedor_id INTEGER NOT NULL REFERENCES proveedores(id),
    usuario_id INTEGER NOT NULL REFERENCES usuarios(id),
    fecha_compra TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10, 2) NOT NULL
);
 
CREATE TABLE lotes (
    id SERIAL PRIMARY KEY,
    producto_id INTEGER NOT NULL REFERENCES productos(id),
    compra_id INTEGER REFERENCES compras(id),
    codigo_lote VARCHAR(50),
    fecha_ingreso TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    cantidad_inicial INTEGER NOT NULL,
    cantidad_actual INTEGER NOT NULL,
    costo_unitario DECIMAL(10, 2) NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);
 
CREATE TABLE movimientos (
    id SERIAL PRIMARY KEY,
    producto_id INTEGER NOT NULL REFERENCES productos(id),
    usuario_id INTEGER NOT NULL REFERENCES usuarios(id),
    tipo_movimiento VARCHAR(20) NOT NULL, -- 'ENTRADA' o 'SALIDA'
    cantidad INTEGER NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    motivo VARCHAR(255) NOT NULL
);
 
CREATE TABLE detalle_compras (
    id SERIAL PRIMARY KEY,
    compra_id INTEGER NOT NULL REFERENCES compras(id),
    producto_id INTEGER NOT NULL REFERENCES productos(id),
    lote_generado_id INTEGER REFERENCES lotes(id),
    cantidad INTEGER NOT NULL,
    costo_unitario DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL
);
 
CREATE TABLE ventas (
    id SERIAL PRIMARY KEY,
    cliente_id INTEGER NOT NULL REFERENCES clientes(id),
    usuario_id INTEGER NOT NULL REFERENCES usuarios(id),
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    subtotal DECIMAL(10, 2) NOT NULL,
    iva DECIMAL(10, 2) NOT NULL,
    total DECIMAL(10, 2) NOT NULL
);
 
CREATE TABLE detalle_ventas (
    id SERIAL PRIMARY KEY,
    venta_id INTEGER NOT NULL REFERENCES ventas(id),
    producto_id INTEGER NOT NULL REFERENCES productos(id),
    lote_id INTEGER REFERENCES lotes(id),
    cantidad INTEGER NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL
);
 
CREATE TABLE logs_sistema (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER NOT NULL REFERENCES usuarios(id),
    accion VARCHAR(100) NOT NULL,
    modulo VARCHAR(100) NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    descripcion_detallada TEXT NOT NULL
);
 