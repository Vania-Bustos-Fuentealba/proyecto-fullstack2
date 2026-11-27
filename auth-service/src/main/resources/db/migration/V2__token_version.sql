-- Invalida JWT anteriores al cambio de contraseña sin guardar tokens en la base.
ALTER TABLE auth_users ADD COLUMN token_version BIGINT NOT NULL DEFAULT 0;
