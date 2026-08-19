# Shipping

A parcel shipping and airline route finder application built with Spring Boot.

## Technologies

- Java 21
- Spring Boot 3.4.1
- Spring Security (Basic Auth)
- Spring Data JPA
- H2 in-memory database
- Maven
- Docker

## Running

### Locally with Maven

```bash
mvn package -DskipTests
java -jar target/shipping-0.0.1-SNAPSHOT.jar
```

Application available at: `http://localhost:8080`

### With Docker Compose

```bash
docker-compose up --build
```

Application available at: `http://localhost:8081`

## Authentication

API endpoints are protected with Basic Auth.

| Username | Password |
|---|---|
| admin | admin |

## H2 Console

Available without authentication: `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:shipping`
- Username: `sa`
- Password: (empty)

## API Endpoints

### Shipping

Base path: `/shipping`

#### Get all clients

| Property | Value |
|---|---|
| Endpoint | `GET /shipping/clients` |
| Description | Retrieve all clients |
| Request body | none |
| Response | `GetClientsResponse` — list of clients |

#### Get all parcels

| Property | Value |
|---|---|
| Endpoint | `GET /shipping/parcels` |
| Description | Retrieve all parcels |
| Request body | none |
| Response | `GetParcelsResponse` — list of parcels |

#### Get parcels by sender

| Property | Value |
|---|---|
| Endpoint | `GET /shipping/parcels/sender` |
| Description | Retrieve parcels by sender ID |
| Request body | `GetParcelsBySenderRequest` — `senderId` (required) |
| Response | `GetParcelsBySenderResponse` — list of parcels |

#### Get parcels by addressee

| Property | Value |
|---|---|
| Endpoint | `GET /shipping/parcels/addressee` |
| Description | Retrieve parcels by addressee ID |
| Request body | `GetParcelsByAddresseeRequest` — `addresseeId` (required) |
| Response | `GetParcelsByAddresseeResponse` — list of parcels |

#### Get parcel events

| Property | Value |
|---|---|
| Endpoint | `GET /shipping/parcels/events` |
| Description | Retrieve tracking events for a parcel |
| Request body | `GetParcelsEventsRequest` — `parcelId` (required) |
| Response | `GetParcelsEventsResponse` — list of tracking events |

### Airline Route Finder

Base path: `/route`

The route finder models an airline's network as a bidirectional weighted graph (cities as nodes, routes as edges) and computes the shortest path by distance using Dijkstra's algorithm. The database is seeded with 10 cities across Europe and the Americas and 15 connecting routes on startup.

#### Get all cities

| Property | Value |
|---|---|
| Endpoint | `GET /route/cities` |
| Description | Retrieve all available cities in the airline network |
| Query params | none |
| Response | `GetCitiesResponse` — list of city objects (`id`, `cityName`, `country`, `continent`) |

#### Get shortest route

| Property | Value |
|---|---|
| Endpoint | `GET /route/shortest` |
| Description | Compute the shortest path between two cities by distance |
| Query params | `departureCityId` (required), `arrivalCityId` (required) |
| Response | `GetShortestRouteResponse` — ordered list of cities in the path, `totalDistanceKm`, `totalCostEur` |

Example request:

```
GET /route/shortest?departureCityId=1&arrivalCityId=6
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
  "totalCostEur": 710
}
```

Error responses:

| Condition | HTTP Status | Message |
|---|---|---|
| Missing `departureCityId` or `arrivalCityId` | 400 | Field is mandatory |
| `departureCityId` equals `arrivalCityId` | 400 | Departure and arrival city must be different |
| City ID not found in database | 404 | City with id {id} not found |
| No path exists between the two cities | 404 | No route found between the specified cities |

### Seeded Cities

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
