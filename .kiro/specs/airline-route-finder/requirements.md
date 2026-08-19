# Requirements Document

## Introduction

An airline offers travel options to major cities across Europe and the Americas. Some routes require layovers (transfers). The system must store cities and direct flight connections (routes) in an H2 database, seed initial data on startup, and expose REST endpoints to list cities and find the shortest path between any two cities using Dijkstra's algorithm. The new tables coexist with the existing shipping tables in the same H2 in-memory database under the `shipping` schema.

## Glossary

- **Route_Finder_System**: The Spring Boot backend module responsible for managing airline cities, routes, and computing shortest paths.
- **City**: A major city in Europe or the Americas served by the airline, stored in the database.
- **Route**: A direct flight connection between two cities with an associated distance (in km) and cost (in EUR).
- **Shortest_Path**: The sequence of cities from a departure city to an arrival city that minimizes total distance, computed using Dijkstra's algorithm.
- **Dijkstra_Algorithm**: A graph traversal algorithm that finds the shortest path between nodes in a weighted graph with non-negative edge weights.
- **RouteController**: A dedicated Spring Boot REST controller handling all route-finding-related endpoints, separate from the existing `ShippingController`.

## Requirements

### Requirement 1: City Database Table

**User Story:** As a developer, I want cities stored in a dedicated database table, so that the system can maintain a registry of all served destinations.

#### Acceptance Criteria

1. THE Route_Finder_System SHALL define a `cities` table in the `shipping` schema with columns: `id` (INT, auto-increment, primary key), `city_name` (VARCHAR, not null), `country` (CHAR(2), ISO 3166-1 alpha-2 country code, not null), and `continent` (VARCHAR, not null).
2. WHEN the application starts, THE Route_Finder_System SHALL create the `cities` table by appending its definition to the existing `schema.sql` file.
3. WHEN the application starts, THE Route_Finder_System SHALL seed at least 10 cities (minimum 5 European and 5 American) into the `cities` table by appending insert statements to the existing `data.sql` file.
4. THE Route_Finder_System SHALL enforce that `city_name` combined with `country` is unique across all rows in the `cities` table.

### Requirement 2: Route Database Table

**User Story:** As a developer, I want direct flight connections stored in a database table with distance and cost, so that the system can model the airline's route network as a weighted graph.

#### Acceptance Criteria

1. THE Route_Finder_System SHALL define a `routes` table in the `shipping` schema with columns: `id` (INT, auto-increment, primary key), `departure_city_id` (INT, foreign key to `cities.id`, not null), `arrival_city_id` (INT, foreign key to `cities.id`, not null), `distance_km` (INT, not null), and `cost_eur` (INT, not null).
2. WHEN the application starts, THE Route_Finder_System SHALL create the `routes` table by appending its definition to the existing `schema.sql` file.
3. THE Route_Finder_System SHALL enforce that `departure_city_id` and `arrival_city_id` reference valid entries in the `cities` table via foreign key constraints.
4. THE Route_Finder_System SHALL treat routes as bidirectional: a single row represents a connection usable in both directions.
5. WHEN the application starts, THE Route_Finder_System SHALL seed at least 15 routes into the `routes` table by appending insert statements to the existing `data.sql` file, forming a connected graph where every city is reachable from every other city through some sequence of routes.
6. THE Route_Finder_System SHALL enforce that `distance_km` and `cost_eur` are positive values.

### Requirement 3: List Cities Endpoint

**User Story:** As an API consumer, I want to retrieve the list of all available cities, so that I can present departure and arrival options to the user.

#### Acceptance Criteria

1. THE Route_Finder_System SHALL expose a GET endpoint at `/route/cities` in a dedicated `RouteController` that returns all cities.
2. WHEN the endpoint is called, THE Route_Finder_System SHALL return a JSON response containing a list of city objects, each with `id`, `cityName`, `country`, and `continent` fields.
3. THE Route_Finder_System SHALL return HTTP 200 status for a successful response.
4. THE Route_Finder_System SHALL require Basic Authentication for the endpoint (using existing Spring Security configuration).

### Requirement 4: Get Routes Between Two Cities Endpoint

**User Story:** As an API consumer, I want to get all possible routes between a departure and arrival city, so that I can see the shortest path along with its total cost and distance.

#### Acceptance Criteria

1. THE Route_Finder_System SHALL expose a GET endpoint at `/route/shortest` in a dedicated `RouteController` that accepts `departureCityId` and `arrivalCityId` as required query parameters.
2. WHEN valid departure and arrival city IDs are provided, THE Route_Finder_System SHALL compute the shortest path using Dijkstra's algorithm with distance as the edge weight.
3. WHEN valid departure and arrival city IDs are provided, THE Route_Finder_System SHALL return a JSON response containing: the ordered list of cities in the shortest path, the total distance in km, and the total cost in EUR.
4. IF the departure city ID or arrival city ID does not exist in the database, THEN THE Route_Finder_System SHALL return HTTP 404 with a descriptive error message.
5. IF the departure city ID equals the arrival city ID, THEN THE Route_Finder_System SHALL return HTTP 400 with a descriptive error message.
6. IF no path exists between the two cities, THEN THE Route_Finder_System SHALL return HTTP 404 with a message indicating no route was found.
7. THE Route_Finder_System SHALL require Basic Authentication for the endpoint (using existing Spring Security configuration).

### Requirement 5: Dijkstra's Algorithm Implementation

**User Story:** As a developer, I want the shortest path computation to use Dijkstra's algorithm, so that the route finding is efficient and deterministic for non-negative edge weights.

#### Acceptance Criteria

1. THE Route_Finder_System SHALL implement Dijkstra's algorithm to compute the shortest path based on distance (km) between two cities.
2. THE Route_Finder_System SHALL implement Dijkstra's algorithm in a dedicated `DijkstraPathFinder` class, separate from the use case class, colocated in the same package as `GetShortestRouteUseCase`.
3. THE Route_Finder_System SHALL treat the route graph as undirected (bidirectional routes).
4. THE Route_Finder_System SHALL accumulate both total distance and total cost along the computed shortest path.
5. WHEN multiple paths have equal shortest distance, THE Route_Finder_System SHALL return any one of them deterministically.

### Requirement 6: Seed Data Quality

**User Story:** As a developer, I want realistic seed data with varied distances and costs, so that the Dijkstra algorithm produces meaningful shortest paths during development and testing.

#### Acceptance Criteria

1. THE Route_Finder_System SHALL seed cities that include at minimum: Budapest, London, Paris, Berlin, Rome, New York, Los Angeles, Toronto, São Paulo, and Mexico City.
2. THE Route_Finder_System SHALL seed routes with realistic approximate distances between the connected cities.
3. THE Route_Finder_System SHALL seed routes with cost values that generally correlate with distance but allow variation (simulating market pricing).
4. THE Route_Finder_System SHALL ensure that the seeded route graph is connected, meaning a path exists between any two cities.
