INSERT INTO clientes(nombre, apellido, email, create_at, foto) VALUES ('ANDRES','GUZMAN','profe@gmail.com','2022-05-28', '');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) VALUES ('JONH','DOE','john.doe@gmail.COM','2022-05-27', '');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) VALUES ('PEPE','FLORES','profe@gmail.com','2022-05-28', '');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) VALUES ('JUAN','CASAS','john.doe@gmail.COM','2022-05-27', '');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) VALUES ('LUIS','MORGAN','profe@gmail.com','2022-05-28', '');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) VALUES ('CARLOS','PEPIN','john.doe@gmail.COM','2022-05-27', '');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) VALUES ('PEDRO','YUMI','profe@gmail.com','2022-05-28', '');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) VALUES ('RICK','TORVALRD','john.doe@gmail.COM','2022-05-27', '');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) VALUES ('DARYL','TORRES','profe@gmail.com','2022-05-28', '');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) VALUES ('SIDERAL','FILL','john.doe@gmail.COM','2022-05-27', '');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) VALUES ('ANDRES','CARRION','profe@gmail.com','2022-05-28', '');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) VALUES ('KRATOS','LUJAN','john.doe@gmail.COM','2022-05-27', '');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) VALUES ('ATREUS','MARSTON','profe@gmail.com','2022-05-28', '');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) VALUES ('ARTHUR','MORGAN','john.doe@gmail.COM','2022-05-27', '');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) VALUES ('PEPE','GUZMAN','profe@gmail.com','2022-05-28', '');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) VALUES ('JONH','MARSTON','john.doe@gmail.COM','2022-05-27', '');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) VALUES ('JACK','GUZMAN','profe@gmail.com','2022-05-28', '');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) VALUES ('MIHCA','BELL  ','john.doe@gmail.COM','2022-05-27', '');

/* productos */
INSERT INTO productos (nombre, precio, create_at) VALUES ('Panasonic LCD',2000, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Sony Camara',2400, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Ipod',3000, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Laptop Gamer',500, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Play Station 5',700, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Nintendo',850, NOW());

/* facturas */
INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura Bicicleta', 'Alguna nota importante!', 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(2, 1, 4);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 3);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 2);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura Prueba', 'Alguna nota importante!', 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(2, 2, 3);

/*Creamos algunos ususario con sus roles*/
INSERT INTO users (username, password, enabled) VALUES ('yimy', '$2a$10$m2I11hkORh7Zr8lieJQ69u8piw3mMFFHWHiKl6NmyBIVGAHW2L8CK', 1)
INSERT INTO users (username, password, enabled) VALUES ('admin', '$2a$10$GhvsFmLtV8O.hsePDaUn4eS2TavupbzlkNN16Ic.6lkp4iSEtU3Vm', 1)

INSERT INTO authorities(user_id, authority) VALUES(1, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(2, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(2, 'ROLE_ADMIN');