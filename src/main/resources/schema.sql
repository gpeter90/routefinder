CREATE SCHEMA IF NOT EXISTS shipping;

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
