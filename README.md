# Shipping

A parcel shipping application built with Spring Boot.

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

Base path: `/shipping`

### Get all clients

| Property | Value |
|---|---|
| Endpoint | `GET /shipping/clients` |
| Description | Retrieve all clients |
| Request body | none |
| Response | `GetClientsResponse` — list of clients |

### Get all parcels

| Property | Value |
|---|---|
| Endpoint | `GET /shipping/parcels` |
| Description | Retrieve all parcels |
| Request body | none |
| Response | `GetParcelsResponse` — list of parcels |

### Get parcels by sender

| Property | Value |
|---|---|
| Endpoint | `GET /shipping/parcels/sender` |
| Description | Retrieve parcels by sender ID |
| Request body | `GetParcelsBySenderRequest` — `senderId` (required) |
| Response | `GetParcelsBySenderResponse` — list of parcels |

### Get parcels by addressee

| Property | Value |
|---|---|
| Endpoint | `GET /shipping/parcels/addressee` |
| Description | Retrieve parcels by addressee ID |
| Request body | `GetParcelsByAddresseeRequest` — `addresseeId` (required) |
| Response | `GetParcelsByAddresseeResponse` — list of parcels |

### Get parcel events

| Property | Value |
|---|---|
| Endpoint | `GET /shipping/parcels/events` |
| Description | Retrieve tracking events for a parcel |
| Request body | `GetParcelsEventsRequest` — `parcelId` (required) |
| Response | `GetParcelsEventsResponse` — list of tracking events |
