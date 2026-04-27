-- ============================================================
-- TripVista Database Schema
-- MySQL 8.0+
-- Run this script to create the database, tables, and sample data
-- ============================================================

-- Create Database
CREATE DATABASE IF NOT EXISTS tripvista_db;
USE tripvista_db;

-- ============================================================
-- TABLE 1: users
-- ============================================================
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(15),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- TABLE 2: flights
-- ============================================================
CREATE TABLE IF NOT EXISTS flights (
    id INT AUTO_INCREMENT PRIMARY KEY,
    airline VARCHAR(50) NOT NULL,
    flight_number VARCHAR(20) NOT NULL,
    from_city VARCHAR(50) NOT NULL,
    to_city VARCHAR(50) NOT NULL,
    from_code VARCHAR(10) NOT NULL,
    to_code VARCHAR(10) NOT NULL,
    departure_time TIME NOT NULL,
    arrival_time TIME NOT NULL,
    duration VARCHAR(20) NOT NULL,
    stops VARCHAR(30) DEFAULT 'Non-Stop',
    price DECIMAL(10,2) NOT NULL,
    available_seats INT DEFAULT 60
);

-- ============================================================
-- TABLE 3: hotels
-- ============================================================
CREATE TABLE IF NOT EXISTS hotels (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    city VARCHAR(50) NOT NULL,
    location VARCHAR(100),
    star_rating INT DEFAULT 3,
    rating_score DECIMAL(3,1) DEFAULT 0.0,
    rating_text VARCHAR(20) DEFAULT 'Good',
    price_per_night DECIMAL(10,2) NOT NULL,
    original_price DECIMAL(10,2),
    amenities VARCHAR(255),
    image_url VARCHAR(500)
);

-- ============================================================
-- TABLE 4: trains
-- ============================================================
CREATE TABLE IF NOT EXISTS trains (
    id INT AUTO_INCREMENT PRIMARY KEY,
    train_name VARCHAR(100) NOT NULL,
    train_number VARCHAR(20) NOT NULL,
    train_type VARCHAR(30) NOT NULL,
    from_station VARCHAR(50) NOT NULL,
    to_station VARCHAR(50) NOT NULL,
    from_code VARCHAR(10) NOT NULL,
    to_code VARCHAR(10) NOT NULL,
    departure_time TIME NOT NULL,
    arrival_time TIME NOT NULL,
    duration VARCHAR(20) NOT NULL,
    stops INT DEFAULT 0,
    runs_on VARCHAR(50) DEFAULT 'Daily',
    price_sleeper DECIMAL(10,2),
    price_3a DECIMAL(10,2),
    price_2a DECIMAL(10,2),
    price_1a DECIMAL(10,2),
    available_seats INT DEFAULT 100
);

-- ============================================================
-- TABLE 5: buses
-- ============================================================
CREATE TABLE IF NOT EXISTS buses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    operator VARCHAR(100) NOT NULL,
    bus_type VARCHAR(50) NOT NULL,
    bus_type_category VARCHAR(30) NOT NULL,
    from_city VARCHAR(50) NOT NULL,
    to_city VARCHAR(50) NOT NULL,
    departure_time TIME NOT NULL,
    arrival_time TIME NOT NULL,
    duration VARCHAR(20) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    rating DECIMAL(3,1) DEFAULT 0.0,
    total_reviews INT DEFAULT 0,
    amenities VARCHAR(255),
    seats_left INT DEFAULT 30
);

-- ============================================================
-- TABLE 6: packages (holiday packages)
-- ============================================================
CREATE TABLE IF NOT EXISTS packages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    destination VARCHAR(100) NOT NULL,
    title VARCHAR(150) NOT NULL,
    duration_nights INT NOT NULL,
    duration_days INT NOT NULL,
    price_per_person DECIMAL(10,2) NOT NULL,
    original_price DECIMAL(10,2),
    includes VARCHAR(500),
    image_url VARCHAR(500),
    rating DECIMAL(3,1) DEFAULT 0.0
);

-- ============================================================
-- TABLE 7: bookings
-- ============================================================
CREATE TABLE IF NOT EXISTS bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    booking_type ENUM('flight', 'hotel', 'train', 'bus', 'package') NOT NULL,
    reference_id INT NOT NULL,
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    travel_date DATE NOT NULL,
    passengers INT DEFAULT 1,
    total_price DECIMAL(10,2) NOT NULL,
    status ENUM('confirmed', 'cancelled', 'pending') DEFAULT 'confirmed',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ============================================================
-- TABLE 8: newsletter
-- ============================================================
CREATE TABLE IF NOT EXISTS newsletter (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(150) NOT NULL UNIQUE,
    subscribed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- ============================================================
-- SAMPLE DATA
-- ============================================================

-- Sample Users (passwords are plain text for demo; use hashing in production)
INSERT INTO users (full_name, email, password, phone) VALUES
('Arjun Mehta', 'arjun@example.com', 'password123', '9876543210'),
('Priya Sharma', 'priya@example.com', 'password123', '9876543211'),
('Ravi Kumar', 'ravi@example.com', 'password123', '9876543212');

-- Sample Flights (Delhi → Mumbai)
INSERT INTO flights (airline, flight_number, from_city, to_city, from_code, to_code, departure_time, arrival_time, duration, stops, price, available_seats) VALUES
('IndiGo', '6E-2154', 'Delhi', 'Mumbai', 'DEL', 'BOM', '06:30:00', '08:40:00', '2h 10m', 'Non-Stop', 3249.00, 45),
('Air India', 'AI-865', 'Delhi', 'Mumbai', 'DEL', 'BOM', '09:15:00', '11:30:00', '2h 15m', 'Non-Stop', 4122.00, 32),
('Vistara', 'UK-953', 'Delhi', 'Mumbai', 'DEL', 'BOM', '12:45:00', '14:50:00', '2h 05m', 'Non-Stop', 4899.00, 28),
('SpiceJet', 'SG-8112', 'Delhi', 'Mumbai', 'DEL', 'BOM', '15:30:00', '19:50:00', '4h 20m', '1 Stop (JAI)', 2599.00, 55),
('AirAsia India', 'I5-741', 'Delhi', 'Mumbai', 'DEL', 'BOM', '18:00:00', '20:20:00', '2h 20m', 'Non-Stop', 2899.00, 38),
('IndiGo', '6E-6094', 'Delhi', 'Mumbai', 'DEL', 'BOM', '21:45:00', '23:55:00', '2h 10m', 'Non-Stop', 3099.00, 50),
('IndiGo', '6E-5102', 'Mumbai', 'Goa', 'BOM', 'GOI', '07:00:00', '08:15:00', '1h 15m', 'Non-Stop', 1999.00, 40),
('Air India', 'AI-440', 'Mumbai', 'Delhi', 'BOM', 'DEL', '10:00:00', '12:10:00', '2h 10m', 'Non-Stop', 3599.00, 35),
('Vistara', 'UK-832', 'Bangalore', 'Delhi', 'BLR', 'DEL', '06:00:00', '08:45:00', '2h 45m', 'Non-Stop', 4599.00, 22),
('IndiGo', '6E-301', 'Chennai', 'Mumbai', 'MAA', 'BOM', '11:30:00', '13:30:00', '2h 00m', 'Non-Stop', 3299.00, 48);

-- Sample Hotels (Goa)
INSERT INTO hotels (name, city, location, star_rating, rating_score, rating_text, price_per_night, original_price, amenities, image_url) VALUES
('Taj Fort Aguada Resort & Spa', 'Goa', 'Sinquerim Beach, North Goa', 5, 9.2, 'Excellent', 9499.00, 14999.00, 'Pool,Spa,Beachside,Free Breakfast', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?w=600&q=80'),
('Grand Hyatt Goa', 'Goa', 'Bambolim, Central Goa', 5, 8.8, 'Very Good', 7299.00, 11500.00, 'Pool,Gym,WiFi,Bar', 'https://images.unsplash.com/photo-1582719508461-905c673771fd?w=600&q=80'),
('Alila Diwa Goa', 'Goa', 'Majorda, South Goa', 4, 8.5, 'Very Good', 5699.00, 8999.00, 'Pool,Spa,WiFi,Restaurant', 'https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?w=600&q=80'),
('Novotel Goa Dona Sylvia', 'Goa', 'Cavelossim, South Goa', 4, 8.1, 'Great', 4199.00, 6500.00, 'Pool,Beach Access,Kids Club', 'https://images.unsplash.com/photo-1571896349842-33c89424de2d?w=600&q=80'),
('Resort Rio', 'Goa', 'Arpora, North Goa', 3, 7.8, 'Good', 2899.00, 4999.00, 'Pool,Water Park,WiFi', 'https://images.unsplash.com/photo-1564501049412-61c2a3083791?w=600&q=80'),
('Goa Marriott Resort & Spa', 'Goa', 'Miramar, Panaji', 5, 8.6, 'Very Good', 8199.00, 12000.00, 'Spa,Casino,Beachfront,WiFi', 'https://images.unsplash.com/photo-1551882547-ff40c63fe5fa?w=600&q=80'),
('The Leela Goa', 'Goa', 'Mobor Beach, South Goa', 5, 9.0, 'Excellent', 11999.00, 18000.00, 'Pool,Spa,Golf,Beach', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?w=600&q=80'),
('ITC Grand Chola', 'Chennai', 'Guindy, Chennai', 5, 9.1, 'Excellent', 8999.00, 13500.00, 'Pool,Spa,Gym,WiFi,Restaurant', 'https://images.unsplash.com/photo-1582719508461-905c673771fd?w=600&q=80'),
('Taj Palace Delhi', 'Delhi', 'Diplomatic Enclave, Delhi', 5, 9.3, 'Excellent', 12999.00, 19000.00, 'Pool,Spa,Gym,WiFi,Restaurant,Bar', 'https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?w=600&q=80'),
('JW Marriott Mumbai', 'Mumbai', 'Juhu Beach, Mumbai', 5, 8.9, 'Very Good', 10499.00, 15500.00, 'Pool,Spa,Beachfront,WiFi,Restaurant', 'https://images.unsplash.com/photo-1571896349842-33c89424de2d?w=600&q=80');

-- Sample Trains (New Delhi → Mumbai)
INSERT INTO trains (train_name, train_number, train_type, from_station, to_station, from_code, to_code, departure_time, arrival_time, duration, stops, runs_on, price_sleeper, price_3a, price_2a, price_1a, available_seats) VALUES
('Rajdhani Express', '12952', 'Rajdhani', 'New Delhi', 'Mumbai Central', 'NDLS', 'BCT', '16:55:00', '08:35:00', '15h 40m', 4, 'Daily', NULL, 1875.00, 2635.00, 4470.00, 42),
('Mumbai Rajdhani', '12954', 'Rajdhani', 'New Delhi', 'Mumbai Central', 'NDLS', 'MMCT', '16:25:00', '08:35:00', '16h 10m', 5, 'Daily', NULL, 1925.00, 2720.00, 4595.00, 18),
('Garib Rath Express', '12909', 'Superfast', 'Hazrat Nizamuddin', 'Mumbai Central', 'NZM', 'BCT', '14:40:00', '08:30:00', '17h 50m', 6, 'Tue,Thu,Sun', NULL, 895.00, NULL, NULL, 118),
('Golden Temple Mail', '12904', 'Express', 'New Delhi', 'Mumbai Central', 'NDLS', 'MMCT', '21:25:00', '16:40:00', '19h 15m', 11, 'Daily', 545.00, 1440.00, 2065.00, 3490.00, 65),
('August Kranti Rajdhani', '12954', 'Rajdhani', 'Hazrat Nizamuddin', 'Mumbai Central', 'NZM', 'BCT', '17:40:00', '10:35:00', '16h 55m', 6, 'Daily', NULL, 1955.00, 2780.00, 4650.00, 15),
('Punjab Mail', '12138', 'Express', 'New Delhi', 'CSMT Mumbai', 'NDLS', 'CSMT', '19:15:00', '17:35:00', '22h 20m', 14, 'Daily', 480.00, 1290.00, 1855.00, 3120.00, 85),
('Shatabdi Express', '12009', 'Shatabdi', 'New Delhi', 'Chandigarh', 'NDLS', 'CDG', '07:15:00', '10:30:00', '3h 15m', 1, 'Daily', NULL, NULL, NULL, 1200.00, 90),
('Chennai Rajdhani', '12434', 'Rajdhani', 'New Delhi', 'Chennai', 'NDLS', 'MAS', '15:55:00', '20:10:00', '28h 15m', 7, 'Wed,Fri', NULL, 2430.00, 3520.00, 5890.00, 30);

-- Sample Buses (Pune → Mumbai)
INSERT INTO buses (operator, bus_type, bus_type_category, from_city, to_city, departure_time, arrival_time, duration, price, rating, total_reviews, amenities, seats_left) VALUES
('MSRTC Shivneri', 'Volvo AC Semi-Sleeper', 'Volvo', 'Pune', 'Mumbai', '06:00:00', '09:30:00', '3h 30m', 499.00, 4.5, 2100, 'WiFi,Charging,Live Tracking,Water Bottle', 22),
('Neeta Travels', 'Volvo Multi-Axle AC Sleeper', 'Volvo', 'Pune', 'Mumbai', '22:00:00', '02:00:00', '4h 00m', 899.00, 4.3, 1800, 'WiFi,Charging,Blanket,Live Tracking,Water Bottle', 8),
('VRL Travels', 'AC Sleeper (2+1)', 'AC Sleeper', 'Pune', 'Mumbai', '21:30:00', '01:45:00', '4h 15m', 749.00, 3.9, 945, 'Charging,Live Tracking', 15),
('Purple Travels', 'Volvo AC Multi-Axle Seater', 'Volvo', 'Pune', 'Mumbai', '07:30:00', '11:15:00', '3h 45m', 549.00, 4.2, 1300, 'WiFi,Charging,Water Bottle', 30),
('Konduskar Travels', 'Non-AC Sleeper (2+1)', 'Non-AC', 'Pune', 'Mumbai', '23:00:00', '04:00:00', '5h 00m', 399.00, 3.7, 680, 'Live Tracking', 18),
('MSRTC Ashwamedh', 'AC Seater Push-Back', 'AC Seater', 'Pune', 'Mumbai', '14:00:00', '17:45:00', '3h 45m', 449.00, 4.1, 1500, 'Charging,Live Tracking,Water Bottle', 25),
('Neeta Travels', 'Volvo AC Seater', 'Volvo', 'Mumbai', 'Pune', '08:00:00', '11:30:00', '3h 30m', 550.00, 4.4, 1650, 'WiFi,Charging,Water Bottle', 20),
('VRL Travels', 'Volvo Multi-Axle Sleeper', 'Volvo', 'Mumbai', 'Bangalore', '20:00:00', '06:00:00', '10h 00m', 1299.00, 4.1, 920, 'WiFi,Charging,Blanket,Live Tracking', 12);

-- Sample Holiday Packages
INSERT INTO packages (destination, title, duration_nights, duration_days, price_per_person, original_price, includes, image_url, rating) VALUES
('Goa', 'Amazing Goa Beach Getaway', 3, 4, 5999.00, 9999.00, 'Stay,Breakfast,Sightseeing,Airport Transfer', 'https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?w=600&q=80', 4.5),
('Manali', 'Manali Snow Adventure', 4, 5, 7499.00, 12999.00, 'Stay,All Meals,Sightseeing,Adventure Activities', 'https://images.unsplash.com/photo-1626621341517-bbf3d9990a23?w=600&q=80', 4.6),
('Rajasthan', 'Royal Rajasthan Tour', 5, 6, 11999.00, 18999.00, 'Stay,Breakfast,Sightseeing,Desert Safari,Cultural Show', 'https://images.unsplash.com/photo-1599661046289-e31897846e41?w=600&q=80', 4.7),
('Kerala', 'Kerala Backwater Experience', 4, 5, 8999.00, 14999.00, 'Stay,All Meals,Houseboat,Ayurveda Spa', 'https://images.unsplash.com/photo-1602216056096-3b40cc0c9944?w=600&q=80', 4.8),
('Andaman', 'Andaman Island Paradise', 5, 6, 15999.00, 24999.00, 'Stay,All Meals,Ferry,Scuba Diving,Snorkeling', 'https://images.unsplash.com/photo-1544550581-5f7ceaf7f793?w=600&q=80', 4.4),
('Dubai', 'Dubai Luxury Escape', 4, 5, 29999.00, 45999.00, 'Stay,Breakfast,Desert Safari,Burj Khalifa,City Tour', 'https://images.unsplash.com/photo-1512453979798-5ea266f8880c?w=600&q=80', 4.5),
('Bali', 'Bali Tropical Retreat', 5, 6, 35999.00, 52999.00, 'Stay,Breakfast,Temple Tour,Beach Activities,Spa', 'https://images.unsplash.com/photo-1537996194471-e657df975ab4?w=600&q=80', 4.7),
('Bangkok', 'Bangkok Explorer Package', 3, 4, 18999.00, 28999.00, 'Stay,Breakfast,City Tour,Temple Visit,Shopping Tour', 'https://images.unsplash.com/photo-1508009603885-50cf7c579365?w=600&q=80', 4.3);

-- Sample Newsletter Subscribers
INSERT INTO newsletter (email) VALUES
('travel.lover@gmail.com'),
('wanderlust2024@yahoo.com'),
('explorer.india@outlook.com');
