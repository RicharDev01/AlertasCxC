
/**
 * Author:  ricardopineda.dev
 * Created: 18 jun. 2025
 */

-- ========================================
-- Tablas base del sistema de Cuentas por Cobrar con alertas
-- ========================================

-- ===========================
-- USUARIOS DEL SISTEMA
-- ===========================
CREATE TABLE usuario (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nombre_completo VARCHAR(100),
    correo VARCHAR(100),
    activo BOOLEAN DEFAULT TRUE,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ===========================
-- ROLES DEL SISTEMA
-- ===========================
CREATE TABLE rol (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL
);

-- ===========================
-- RELACIÓN MUCHOS A MUCHOS ENTRE USUARIO Y ROL
-- ===========================
CREATE TABLE usuario_rol (
    usuario_id BIGINT NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    rol_id BIGINT NOT NULL REFERENCES rol(id) ON DELETE CASCADE,
    PRIMARY KEY (usuario_id, rol_id)
);

-- Tipos de documento (Ej: Factura, CCF, Quedan)
CREATE TABLE tipo_documento (
    id_tipo_documento BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(30) UNIQUE NOT NULL
);

-- Estados posibles del documento
CREATE TABLE estado_documento (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(30) UNIQUE NOT NULL
);

-- Entidades que reciben crédito: aseguradoras, médicos, embajadas, etc.
CREATE TABLE entidad_cobrable (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    correo VARCHAR(100),
    direccion TEXT
);


-- Configuración de prórrogas por tipo de documento y entidad
CREATE TABLE config_vencimiento (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    entidad_cobrable_id BIGINT NOT NULL UNIQUE 
      REFERENCES entidad_cobrable(id),
    dias_prorroga INT NOT NULL CHECK (dias_prorroga >= 0),

);

-- Documentos registrados en cuentas por cobrar
CREATE TABLE documento_cxc (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    tipo_documento_id BIGINT NOT NULL REFERENCES tipo_documento(id),
    numero_documento VARCHAR(50) NOT NULL,
    fecha_factura DATE NOT NULL,
    fecha_recepcion DATE,
    fecha_emision_quedan DATE,
    monto DECIMAL(10,2) NOT NULL CHECK (monto >= 0),

    entidad_cobrable_id BIGINT REFERENCES entidad_cobrable(id),
    fecha_vencimiento DATE, -- Calculado por la UI
    estado_documento_id BIGINT REFERENCES estado_documento(id),

    observaciones TEXT,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP
);


-- Bitácora de cambios de estado (auditoría)
CREATE TABLE log_estado_documento (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    documento_cxc_id BIGINT NOT NULL REFERENCES documento_cxc(id) ON DELETE CASCADE,
    estado_anterior_id BIGINT REFERENCES estado_documento(id),
    estado_nuevo_id BIGINT REFERENCES estado_documento(id),
    cambiado_por VARCHAR(100),
    cambiado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================
-- DATOS INICIALES
-- =====================================

-- ROLES
INSERT INTO rol (nombre) VALUES
('ADMIN'),
('SUPERVISOR'),
('CONSULTA');

-- USUARIOS
INSERT INTO usuario (username, password, nombre_completo, correo) VALUES
('admin', 'admin123encriptado', 'Administrador General', 'admin@sistema.com'),
('cxc_user', 'cxc123encriptado', 'Usuario CxC', 'cxc@sistema.com');

-- USUARIO ↔ ROL
INSERT INTO usuario_rol (usuario_id, rol_id) VALUES
(1, 1), -- admin -> ADMIN
(2, 2); -- cxc_user -> SUPERVISOR

-- Tipos de Documento
INSERT INTO tipo_documento (nombre) VALUES
('Factura'),
('Crédito Fiscal (CCF)'),
('Quedan');

-- Estados del documento
INSERT INTO estado_documento (nombre) VALUES
('Pendiente'),
('Pagado'),
('Devuelto'),
('Anulado'),
('Cerrado');

-- Entidades cobrables
INSERT INTO entidad_cobrable (nombre, telefono, correo, direccion) VALUES
('ASESUISA', '2222-1111', 'contacto@asesuisa.com', 'San Salvador, Edificio ASES'),
('MAPFRE', '2222-2222', 'mapfre@seguros.com', 'Santa Elena, Antiguo Cuscatlán'),
('PALIG', '2222-3333', 'info@palig.com', 'Colonia Escalón, San Salvador'),
('RPN', '2222-4444', 'contacto@rpn.com', 'Boulevard Los Próceres'),
('EMBAJADA DE ESTADOS UNIDOS', '2222-5555', 'usa.embajada@state.gov', 'Antiguo Cuscatlán, La Libertad'),
('Dr. Juan Pérez - Neurólogo', '2222-6666', 'juan.perez@medicos.com', 'Clínica Médica Los Héroes'),
('HOSPITAL ROSALES', '2222-7777', 'rosales@salud.gob.sv', 'Centro de Gobierno, San Salvador'),
('HOSPITAL ZACAMIL', '2222-8888', 'zacamil@salud.gob.sv', 'Mejicanos, San Salvador');

-- Configuración de prórroga por entidad
INSERT INTO config_vencimiento (entidad_cobrable_id, dias_prorroga) VALUES
(1, 90),  -- ASESUISA
(2, 90),  -- MAPFRE
(3, 60),  -- PALIG
(4, 90),  -- RPN
(5, 90),  -- Embajada
(6, 30),  -- Dr. Juan Pérez
(7, 45),  -- Hospital Rosales
(8, 60);  -- Hospital Zacamil

-- Documentos de ejemplo
INSERT INTO documento_cxc (
    tipo_documento_id, numero_documento, fecha_factura, fecha_recepcion,
    fecha_emision_quedan, monto, entidad_cobrable_id, fecha_vencimiento,
    estado_documento_id, observaciones
) VALUES
(1, 'FAC-001', '2025-04-01', '2025-04-03', NULL, 150.00, 1, '2025-06-30', 1, 'Factura ASESUISA - en prórroga'),
(2, 'CCF-1023', '2025-03-15', '2025-03-17', NULL, 200.00, 2, '2025-06-15', 2, 'Pagado por MAPFRE'),
(3, 'QDN-556', '2025-05-05', '2025-05-06', '2025-05-08', 300.00, 3, '2025-07-07', 1, 'Pendiente por PALIG'),
(1, 'FAC-045', '2025-01-20', '2025-01-22', NULL, 180.00, 6, '2025-02-19', 3, 'Devuelta por el médico'),
(2, 'CCF-2099', '2025-04-10', '2025-04-12', NULL, 275.00, 8, '2025-07-11', 1, 'Zacamil, aún pendiente');
