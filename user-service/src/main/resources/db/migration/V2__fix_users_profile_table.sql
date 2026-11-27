-- La tabla nueva de perfiles no contiene contraseñas. Solo se adapta una tabla
-- heredada si esa columna existe; así una instalación limpia no falla en V2.
SET @has_password = (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = DATABASE() AND table_name = 'users' AND column_name = 'password'
);
SET @profile_sql = IF(@has_password > 0,
    'ALTER TABLE users MODIFY password VARCHAR(255) NULL', 'SELECT 1');
PREPARE profile_statement FROM @profile_sql;
EXECUTE profile_statement;
DEALLOCATE PREPARE profile_statement;
