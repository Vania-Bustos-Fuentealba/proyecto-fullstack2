-- Amplía el modelo sin borrar cuentas previas ni inventar datos personales.
ALTER TABLE auth_users
 ADD COLUMN run VARCHAR(9) NULL,
 ADD COLUMN last_name VARCHAR(100) NULL,
 ADD COLUMN birth_date DATE NULL,
 ADD COLUMN region VARCHAR(100) NULL,
 ADD COLUMN commune VARCHAR(100) NULL,
 ADD COLUMN address VARCHAR(300) NULL,
 ADD UNIQUE KEY uk_auth_users_run (run);
