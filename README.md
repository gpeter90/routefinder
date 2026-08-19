# Shipping

Csomagküldő alkalmazás Spring Boot alapokon.

## Technológiák

- Java 21
- Spring Boot 3.4.1
- Spring Security (Basic Auth)
- Spring Data JPA
- H2 in-memory adatbázis
- Maven
- Docker

## Futtatás

### Maven-nel lokálisan

```bash
mvn package -DskipTests
java -jar target/shipping-0.0.1-SNAPSHOT.jar
```

Az alkalmazás elérhető: `http://localhost:8080`

### Docker Compose-zal

```bash
docker-compose up --build
```

Az alkalmazás elérhető: `http://localhost:8081`

## Authentikáció

Az API végpontok Basic Auth-tal védettek.

| Felhasználónév | Jelszó |
|---|---|
| admin | admin |

## H2 Konzol

Elérhető authentikáció nélkül: `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:shipping`
- Username: `sa`
- Password: (üres)

## API Végpontok

Alap útvonal: `/shipping`

### Ügyfelek lekérdezése

| Tulajdonság | Érték |
|---|---|
| Végpont | `GET /shipping/clients` |
| Leírás | Az összes ügyfél lekérdezése |
| Request body | `GetClientsRequest` (üres) |
| Response | `GetClientsResponse` — ügyfelek listája |

### Csomagok lekérdezése

| Tulajdonság | Érték |
|---|---|
| Végpont | `GET /shipping/parcels` |
| Leírás | Az összes csomag lekérdezése |
| Request body | `GetParcelsRequest` (üres) |
| Response | `GetParcelsResponse` — csomagok listája |

### Csomagok feladó szerint

| Tulajdonság | Érték |
|---|---|
| Végpont | `GET /shipping/parcels/sender` |
| Leírás | Csomagok lekérdezése feladó azonosító alapján |
| Request body | `GetParcelsBySenderRequest` — `senderId` (kötelező) |
| Response | `GetParcelsBySenderResponse` — csomagok listája |

### Csomagok címzett szerint

| Tulajdonság | Érték |
|---|---|
| Végpont | `GET /shipping/parcels/addressee` |
| Leírás | Csomagok lekérdezése címzett azonosító alapján |
| Request body | `GetParcelsByAddresseeRequest` — `addresseeId` (kötelező) |
| Response | `GetParcelsByAddresseeResponse` — csomagok listája |

### Csomag eseményei

| Tulajdonság | Érték |
|---|---|
| Végpont | `GET /shipping/parcels/events` |
| Leírás | Egy csomag követési eseményeinek lekérdezése |
| Request body | `GetParcelsEventsRequest` — `parcelId` (kötelező) |
| Response | `GetParcelsEventsResponse` — események listája |


