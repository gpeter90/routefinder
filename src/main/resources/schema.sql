CREATE SCHEMA IF NOT EXISTS shipping;
CREATE SCHEMA IF NOT EXISTS airline;

CREATE TABLE IF NOT EXISTS shipping.clients(
    id INT AUTO_INCREMENT NOT NULL,
    client_name VARCHAR(100) NOT NULL,
    address VARCHAR(200) NOT NULL,
    zipcode VARCHAR(20) NOT NULL,
    city VARCHAR(100) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS shipping.events(
    id SMALLINT AUTO_INCREMENT NOT NULL,
    event_name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS shipping.parcels(
    id INT AUTO_INCREMENT NOT NULL,
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    parcel_no VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (sender_id) REFERENCES shipping.clients(id),
    FOREIGN KEY (receiver_id) REFERENCES shipping.clients(id)
);

CREATE TABLE IF NOT EXISTS shipping.trackings(
    id INT AUTO_INCREMENT NOT NULL,
    parcel_id INT NOT NULL,
    event_date TIMESTAMP NOT NULL,
    event_id SMALLINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (parcel_id) REFERENCES shipping.parcels(id),
    FOREIGN KEY (event_id) REFERENCES shipping.events(id)
);

CREATE TABLE IF NOT EXISTS airline.cities(
    id INT AUTO_INCREMENT NOT NULL,
    city_name VARCHAR(100) NOT NULL,
    country CHAR(2) NOT NULL,
    continent VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_city_country UNIQUE (city_name, country)
);

CREATE TABLE IF NOT EXISTS airline.routes(
    id INT AUTO_INCREMENT NOT NULL,
    departure_city_id INT NOT NULL,
    arrival_city_id INT NOT NULL,
    distance_km INT NOT NULL CHECK (distance_km > 0),
    cost_eur INT NOT NULL CHECK (cost_eur > 0),
    PRIMARY KEY (id),
    FOREIGN KEY (departure_city_id) REFERENCES airline.cities(id),
    FOREIGN KEY (arrival_city_id) REFERENCES airline.cities(id)
);
