-- Pedidos de Vale sobre la única fuente de libros/stock del proyecto integrado.
ALTER TABLE books ADD COLUMN version BIGINT NOT NULL DEFAULT 0;
CREATE TABLE pedidos (
 id BIGINT AUTO_INCREMENT PRIMARY KEY, fecha DATETIME(6) NOT NULL,
 propietario VARCHAR(100) NOT NULL, solicitud VARCHAR(160) NOT NULL UNIQUE, huella VARCHAR(64) NOT NULL,
 cliente VARCHAR(100) NOT NULL, correo VARCHAR(100) NOT NULL, direccion VARCHAR(300) NOT NULL,
 comuna VARCHAR(100) NOT NULL, metodo_pago VARCHAR(20) NOT NULL, cupon VARCHAR(20),
 subtotal DECIMAL(19,2) NOT NULL, descuento DECIMAL(19,2) NOT NULL,
 envio DECIMAL(19,2) NOT NULL, total DECIMAL(19,2) NOT NULL
);
CREATE TABLE pedido_items (
 id BIGINT AUTO_INCREMENT PRIMARY KEY, pedido_id BIGINT NOT NULL, libro_id BIGINT NOT NULL,
 nombre_libro VARCHAR(150) NOT NULL, precio_unitario DECIMAL(19,2) NOT NULL,
 cantidad INT NOT NULL, subtotal DECIMAL(19,2) NOT NULL,
 CONSTRAINT fk_pedido_item_pedido FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
 CONSTRAINT fk_pedido_item_libro FOREIGN KEY (libro_id) REFERENCES books(id)
);
