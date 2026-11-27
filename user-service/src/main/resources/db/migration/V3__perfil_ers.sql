-- Los perfiles existentes se conservan y se completan desde administración.
ALTER TABLE users
 MODIFY address VARCHAR(300) NOT NULL,
 ADD COLUMN run VARCHAR(9) NULL,
 ADD COLUMN last_name VARCHAR(100) NULL,
 ADD COLUMN birth_date DATE NULL,
 ADD COLUMN region VARCHAR(100) NULL,
 ADD COLUMN commune VARCHAR(100) NULL;
