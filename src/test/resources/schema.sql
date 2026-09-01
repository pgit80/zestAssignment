-- Drop tables if they exist to ensure clean execution
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS users;

-- 1. Product Table
CREATE TABLE product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    created_by VARCHAR(100),
    created_on TIMESTAMP,
    modified_by VARCHAR(100),
    modified_on TIMESTAMP
);

-- 2. Item Table
CREATE TABLE item (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    created_by VARCHAR(100),
    created_on TIMESTAMP,
    modified_by VARCHAR(100),
    modified_on TIMESTAMP,
    CONSTRAINT fk_item_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

-- 3. Users Table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);