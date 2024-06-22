-- schema.sql

-- Create the roles table if it does not exist
CREATE TABLE IF NOT EXISTS roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- Insert the 'ADMIN' role if it does not exist
INSERT INTO roles (name)
SELECT 'ADMIN'
WHERE NOT EXISTS (
    SELECT 1 FROM roles WHERE name = 'ADMIN'
);

-- Insert the 'USER' role if it does not exist
INSERT INTO roles (name)
SELECT 'USER'
WHERE NOT EXISTS (
    SELECT 1 FROM roles WHERE name = 'USER'
);