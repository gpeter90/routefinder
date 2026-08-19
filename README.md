# RouteFinder

An airline route finder application built with Spring Boot. Computes the least cost route between cities using Dijkstra's algorithm, where cost is measured in travel time (minutes).

## Technologies

- Java 21
- Spring Boot 3.4.1
- Spring Security (Basic Auth)
- Spring Data JPA
- Spring Boot Actuator
- Jakarta Validation
- H2 in-memory database
- SpringDoc OpenAPI (Swagger UI)
- Lombok
- Apache Commons Lang 3
- Maven
- Logback (SLF4J)

## Architecture

The project follows a `Controller → UseCase → Service` layered architecture:

```text
src/main/java/com/shipping/demo/
├── common/
│   ├── config/          # Security, OpenAPI configuration
│   ├── domain/          # Base entity, domain service abstractions
│   ├── exception/       # Custom exception types
│   ├── rest/            # Global exception handler, error response DTO
│   ├── usecase/         # Abstract UseCase base class
│   ├── util/            # Database constants
│   └── validator/       # Mandatory field, Jakarta, and custom validators
├── controller/
│   └── rest/            # REST controllers
├── domain/
│   ├── city/            # City entity, DTO, repository, service
│   └── route/           # Route entity, DTO, repository, service
├── usecase/
│   ├── getcities/       # GetCitiesUseCase
│   ├── getroutes/       # GetRoutesUseCase
│   └── getshortestroute/# GetShortestRouteUseCase + DijkstraPathFinder
└── RouteFinderApplication.java
```

Each use case extends `UseCase<REQUEST, RESPONSE>` which provides logging, validation (Jakarta + custom validators), and error handling. Controllers remain thin — they delegate to use cases and return their responses.

## Running

### Locally with Maven

```bash
mvn package -DskipTests
java -jar target/routefinder-0.0.1-SNAPSHOT.jar
```

Application available at: `http://localhost:8080`

### With Docker Compose

```bash
docker-compose up --build
```

Application available at: `http://localhost:8081`

Remote debug port: `8001` (JDWP agent enabled in the container).

## Authentication

API endpoints are protected with Basic Auth.

| Username | Password |
|---|---|
| admin | admin |

## H2 Console

Available without authentication: `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:routefinder`
- Username: `sa`
- Password: (empty)

## Swagger UI

Available at: `http://localhost:8080/swagger-ui.html` (requires Basic Auth)

## API Endpoints

Base path: `/routefinder`

The route finder models an airline's network as a bidirectional weighted graph (cities as nodes, routes as edges) and computes the least cost path using Dijkstra's algorithm. The cost of each route is its travel time in minutes. The database is seeded with 10 cities across Europe and the Americas and 15 connecting routes on startup.

### Get all cities

| Property | Value |
|---|---|
| Endpoint | `GET /routefinder/cities` |
| Description | Retrieve all available cities in the airline network |
| Query params | none |
| Response | List of city objects (`id`, `cityName`, `country`, `continent`) |

### Get all routes

| Property | Value |
|---|---|
| Endpoint | `GET /routefinder/routes` |
| Description | Retrieve all available routes with distance and travel time (cost) |
| Query params | none |
| Response | List of route objects (`id`, `departureCityName`, `arrivalCityName`, `distanceKm`, `travelTimeMin`) |

### Get least cost route

| Property | Value |
|---|---|
| Endpoint | `GET /routefinder/shortest` |
| Description | Compute the least cost path between two cities (optimized by travel time) |
| Query params | `departureCityId` (required), `arrivalCityId` (required) |
| Response | Ordered list of cities in the path, `totalDistanceKm`, `totalTravelTimeMin` |

Example request:

```
GET /routefinder/shortest?departureCityId=1&arrivalCityId=6
```

Example response:

```json
{
  "path": [
    { "id": 1, "cityName": "Budapest", "country": "HU", "continent": "Europe" },
    { "id": 4, "cityName": "Berlin", "country": "DE", "continent": "Europe" },
    { "id": 2, "cityName": "London", "country": "GB", "continent": "Europe" },
    { "id": 6, "cityName": "New York", "country": "US", "continent": "Americas" }
  ],
  "totalDistanceKm": 7191,
  "totalTravelTimeMin": 745
}
```

### Error responses

| Condition | HTTP Status | Message |
|---|---|---|
| Missing `departureCityId` or `arrivalCityId` | 400 | Field is mandatory |
| `departureCityId` equals `arrivalCityId` | 400 | Departure and arrival cities must be different |
| City ID not found in database | 404 | City not found |
| No path exists between the two cities | 404 | No route found between the specified cities |

## Database Schema

```sql
CREATE TABLE routefinder.cities (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    city_name     VARCHAR(100) NOT NULL,
    country       CHAR(2) NOT NULL,
    continent     VARCHAR(50) NOT NULL,
    UNIQUE (city_name, country)
);

CREATE TABLE routefinder.routes (
    id                INT AUTO_INCREMENT PRIMARY KEY,
    departure_city_id INT NOT NULL REFERENCES cities(id),
    arrival_city_id   INT NOT NULL REFERENCES cities(id),
    distance_km       INT NOT NULL CHECK (distance_km > 0),
    travel_time_min   INT NOT NULL CHECK (travel_time_min > 0)
);
```

## Seeded Cities

| ID | City | Country | Continent |
|---|---|---|---|
| 1 | Budapest | HU | Europe |
| 2 | London | GB | Europe |
| 3 | Paris | FR | Europe |
| 4 | Berlin | DE | Europe |
| 5 | Rome | IT | Europe |
| 6 | New York | US | Americas |
| 7 | Los Angeles | US | Americas |
| 8 | Toronto | CA | Americas |
| 9 | São Paulo | BR | Americas |
| 10 | Mexico City | MX | Americas |

## Seeded Routes

| Departure | Arrival | Distance (km) | Travel Time (min) |
|---|---|---|---|
| Budapest | Berlin | 689 | 105 |
| Budapest | Rome | 810 | 120 |
| Berlin | London | 932 | 130 |
| Berlin | Paris | 878 | 120 |
| London | Paris | 344 | 75 |
| London | New York | 5570 | 510 |
| Paris | Rome | 1105 | 150 |
| Rome | São Paulo | 9170 | 720 |
| New York | Toronto | 550 | 90 |
| New York | Los Angeles | 3944 | 360 |
| New York | São Paulo | 7680 | 660 |
| Los Angeles | Mexico City | 2491 | 270 |
| Toronto | London | 5720 | 480 |
| São Paulo | Mexico City | 7050 | 600 |
| Mexico City | New York | 3364 | 300 |

## Logging

Separate log files for each level in the `logs/` directory:
- `logs/info.log` — INFO level messages
- `logs/debug.log` — DEBUG level messages
- `logs/error.log` — ERROR level messages

Log rotation: 100 KB per file, with timestamp in archived filenames.
