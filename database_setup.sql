-- Construction Cost Estimator Database Setup
-- Run this script in phpMyAdmin (XAMPP) to create the database and tables

-- Create the database
CREATE DATABASE IF NOT EXISTS construction_db;

-- Use the database
USE construction_db;

-- Create Materials table
CREATE TABLE IF NOT EXISTS materials (
    id INT AUTO_INCREMENT PRIMARY KEY,
    project_name VARCHAR(200) DEFAULT 'Default Project',
    material_type VARCHAR(50) NOT NULL,
    unit_cost DOUBLE NOT NULL,
    quantity DOUBLE NOT NULL,
    total_cost DOUBLE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Labor table
CREATE TABLE IF NOT EXISTS labor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    project_name VARCHAR(200) DEFAULT 'Default Project',
    labor_type VARCHAR(100) NOT NULL,
    rate_per_hour DOUBLE NOT NULL,
    hours_worked INT NOT NULL,
    total_cost DOUBLE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Equipment table
CREATE TABLE IF NOT EXISTS equipment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    project_name VARCHAR(200) DEFAULT 'Default Project',
    equipment_name VARCHAR(100) NOT NULL,
    rate_per_day DOUBLE NOT NULL,
    days_used INT NOT NULL,
    total_cost DOUBLE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Projects table (optional, for future use)
CREATE TABLE IF NOT EXISTS projects (
    id INT AUTO_INCREMENT PRIMARY KEY,
    project_name VARCHAR(200) NOT NULL,
    total_material_cost DOUBLE DEFAULT 0,
    total_labor_cost DOUBLE DEFAULT 0,
    total_equipment_cost DOUBLE DEFAULT 0,
    grand_total DOUBLE DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Display success message
SELECT 'Database and tables created successfully!' AS Status;
