CREATE DATABASE IF NOT EXISTS attendance_db;
USE attendance_db;

CREATE TABLE IF NOT EXISTS employee (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(64) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(10) DEFAULT 'user',
    hourly_rate DECIMAL(10,2) DEFAULT 1000.00,
    address VARCHAR(255) DEFAULT '',
    phone VARCHAR(20) DEFAULT '',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS attendance (
    id INT PRIMARY KEY AUTO_INCREMENT,
    employee_id INT NOT NULL,
    date DATE NOT NULL,
    clock_in TIME,
    clock_out TIME,
    note VARCHAR(255),
    FOREIGN KEY (employee_id) REFERENCES employee(id) ON DELETE CASCADE,
    UNIQUE KEY unique_attendance (employee_id, date)
);

CREATE TABLE IF NOT EXISTS break_record (
    id INT PRIMARY KEY AUTO_INCREMENT,
    attendance_id INT NOT NULL,
    break_start TIME NOT NULL,
    break_end TIME NOT NULL,
    FOREIGN KEY (attendance_id) REFERENCES attendance(id) ON DELETE CASCADE
);

INSERT INTO employee (username, password, full_name, role) VALUES
('admin', SHA2('admin123', 256), 'Administrator', 'admin')
ON DUPLICATE KEY UPDATE username=username;
