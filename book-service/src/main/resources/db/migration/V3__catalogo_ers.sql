-- Mantiene los datos previos; los campos nuevos se completan desde el formulario.
ALTER TABLE books
 ADD COLUMN code VARCHAR(100) NULL,
 ADD COLUMN description VARCHAR(500) NULL,
 ADD COLUMN category VARCHAR(100) NULL,
 ADD COLUMN image_url VARCHAR(2000) NULL,
 ADD COLUMN critical_stock INT NULL,
 ADD UNIQUE KEY uk_books_code (code);
