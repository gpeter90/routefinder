# Implementation Plan: Airline Route Finder

## Overview

Implement the airline route finder feature by adding database schema, domain entities, services, repositories, use cases, a Dijkstra algorithm component, and a dedicated REST controller. Each task builds incrementally on the previous, ending with full wiring into the existing Spring Boot application.

## Tasks

- [x] 1. Database schema and seed data
  - [x] 1.1 Append cities and routes table definitions to schema.sql
    - Add `shipping.cities` table with `id`, `city_name`, `country` (CHAR(2)), `continent`, unique constraint on (`city_name`, `country`)
    - Add `shipping.routes` table with `id`, `departure_city_id`, `arrival_city_id`, `distance_km` (CHECK > 0), `cost_eur` (CHECK > 0), foreign keys to `cities`
    - _Requirements: 1.1, 1.2, 2.1, 2.2, 2.3, 2.6_

  - [x] 1.2 Append seed data to data.sql
    - Insert 10 cities (Budapest, London, Paris, Berlin, Rome, New York, Los Angeles, Toronto, São Paulo, Mexico City)
    - Insert 15+ routes with realistic distances and costs forming a connected graph
    - _Requirements: 1.3, 2.5, 6.1, 6.2, 6.3, 6.4_

- [x] 2. Domain entities and repositories
  - [x] 2.1 Create City entity, CityDto, and CityRepository
    - `City.java` extending `BaseEntity` with `cityName`, `country`, `continent` mapped to `shipping.cities`
    - `CityDto.java` with `id`, `cityName`, `country`, `continent`
    - `CityRepository.java` extending `JpaRepository<City, Long>`
    - _Requirements: 1.1, 1.4_

  - [x] 2.2 Create Route entity, RouteDto, and RouteRepository
    - `Route.java` extending `BaseEntity` with `departureCityId`, `arrivalCityId`, `distanceKm`, `costEur` mapped to `shipping.routes`
    - `RouteDto.java` with `id`, `departureCityId`, `arrivalCityId`, `distanceKm`, `costEur`
    - `RouteRepository.java` extending `JpaRepository<Route, Long>`
    - _Requirements: 2.1, 2.4_

- [x] 3. Domain services
  - [x] 3.1 Create CityService
    - Methods: `findAll()` returning `List<CityDto>`, `existsById(Long)`, `findById(Long)` returning `CityDto`
    - Maps `City` entities to `CityDto`
    - _Requirements: 3.1, 3.2_

  - [x] 3.2 Create RouteService
    - Method: `findAll()` returning `List<RouteDto>`
    - Maps `Route` entities to `RouteDto`
    - _Requirements: 4.2_

- [x] 4. Error handling
  - [x] 4.1 Create NotFoundException and register handler
    - `NotFoundException.java` extending `RuntimeException`
    - Add `@ExceptionHandler` method in `ShippingExceptionHandler` mapping to HTTP 404
    - _Requirements: 4.4, 4.6_

- [x] 5. Checkpoint
  - Ensure the application starts successfully with the new schema, seed data, entities, services, and exception handler. Ask the user if questions arise.

- [x] 6. Use cases and algorithm
  - [x] 6.1 Create GetCitiesUseCase with GetCitiesResponse
    - Extends `UseCase<Void, GetCitiesResponse>`
    - Delegates to `CityService.findAll()`
    - `GetCitiesResponse` wraps `List<CityDto>`
    - _Requirements: 3.1, 3.2, 3.3_

  - [x] 6.2 Create DijkstraPathFinder component with DijkstraResult
    - `@Component` in `getshortestroute` package
    - Method `findShortestPath(List<RouteDto>, Long departureCityId, Long arrivalCityId)` returning `DijkstraResult`
    - Builds bidirectional adjacency list, uses `PriorityQueue` by cumulative distance
    - Tracks cumulative cost alongside distance
    - Returns `null` when no path exists
    - `DijkstraResult` with `pathCityIds`, `totalDistanceKm`, `totalCostEur`
    - _Requirements: 5.1, 5.2, 5.3, 5.4, 5.5_

  - [x] 6.3 Create GetShortestRouteUseCase with request and response
    - Extends `UseCase<GetShortestRouteRequest, GetShortestRouteResponse>`
    - `GetShortestRouteRequest` with `departureCityId`, `arrivalCityId` annotated `@MandatoryField`, plus `@Setter`/`@NoArgsConstructor` for query param binding
    - Validates departure ≠ arrival (throws `InvalidParameterException`)
    - Validates city IDs exist (throws `NotFoundException`)
    - Invokes `DijkstraPathFinder.findShortestPath()`
    - Throws `NotFoundException` if no path found
    - Maps `DijkstraResult` city IDs to `CityDto` list in `GetShortestRouteResponse`
    - `GetShortestRouteResponse` with `path`, `totalDistanceKm`, `totalCostEur`
    - _Requirements: 4.1, 4.2, 4.3, 4.4, 4.5, 4.6_

- [x] 7. REST controller
  - [x] 7.1 Create RouteController
    - `@RestController` at `/route`
    - `GET /route/cities` → delegates to `GetCitiesUseCase`
    - `GET /route/shortest?departureCityId=&arrivalCityId=` → delegates to `GetShortestRouteUseCase`
    - Both return HTTP 200 with JSON, secured by existing Basic Auth
    - _Requirements: 3.1, 3.3, 3.4, 4.1, 4.7_

- [x] 8. Final checkpoint
  - Ensure the application compiles, starts, and both endpoints respond correctly with seeded data. Ask the user if questions arise.

## Notes

- The implementation language is Java 21 with Spring Boot 3.4.1
- All new classes follow existing project conventions (Lombok, BaseEntity, UseCase base class)
- Database changes are appended to existing `schema.sql` and `data.sql` — no new migration files
- The `NotFoundException` is introduced to satisfy HTTP 404 requirements without altering existing `BusinessLogicException` → 422 mapping
- No test files are generated per user request

## Task Dependency Graph

```json
{
  "waves": [
    { "id": 0, "tasks": ["1.1"] },
    { "id": 1, "tasks": ["1.2", "2.1", "2.2"] },
    { "id": 2, "tasks": ["3.1", "3.2", "4.1"] },
    { "id": 3, "tasks": ["6.1", "6.2"] },
    { "id": 4, "tasks": ["6.3"] },
    { "id": 5, "tasks": ["7.1"] }
  ]
}
```
