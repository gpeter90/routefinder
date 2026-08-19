CREATE SCHEMA IF NOT EXISTS routefinder;

CREATE TABLE IF NOT EXISTS routefinder.cities(
    id INT AUTO_INCREMENT NOT NULL,
    city_name VARCHAR(100) NOT NULL,
    country CHAR(2) NOT NULL,
    continent VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_city_country UNIQUE (city_name, country)
);

CREATE TABLE IF NOT EXISTS routefinder.routes(
    id INT AUTO_INCREMENT NOT NULL,
    departure_city_id INT NOT NULL,
    arrival_city_id INT NOT NULL,
    distance_km INT NOT NULL CHECK (distance_km > 0),
    travel_time_min INT NOT NULL CHECK (travel_time_min > 0),
    PRIMARY KEY (id),
    FOREIGN KEY (departure_city_id) REFERENCES routefinder.cities(id),
    FOREIGN KEY (arrival_city_id) REFERENCES routefinder.cities(id)
);
