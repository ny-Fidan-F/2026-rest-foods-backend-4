# REST Restaurant Backend

A Spring Boot REST API for managing restaurant menus, tables, and reservations.

## Features

- Create, read, update, and delete menu items
- Filter menu items by category or chef's choice
- Manage restaurant tables
- Check table availability for a selected time range
- Create and manage reservations
- Prevent overlapping reservations
- Validate table capacity and reservation data
- OpenAPI documentation with Swagger UI

## Requirements

- Java 25
- PostgreSQL

## Database Setup

The default database configuration is located in
`rest-restaurant/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=
```

Import the included database schema and sample data:

```bash
psql -h localhost -U postgres -d postgres -f rest-restaurant/database/restaurant_database.sql
```

The script replaces the existing application tables.

## Run the Application

First, open the application directory:

```bash
cd rest-restaurant
```

On Windows:

```bash
.\gradlew.bat bootRun
```

On Linux or macOS:

```bash
./gradlew bootRun
```

The API runs at `http://localhost:8080`.

## API Overview

| Resource | Endpoint | Description |
| --- | --- | --- |
| Menus | `/menus` | Manage and filter menu items |
| Reservations | `/reservations` | Manage reservations |
| Tables | `/tables` | Manage restaurant tables |
| Available tables | `/tables/available?start=...&end=...` | Find available tables |

Menu filters:

```text
GET /menus?category=Pizza
GET /menus?chefsChoice=true
```

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

## Testing

Run the Gradle tests:

```bash
./gradlew test
```

A Postman collection is available in
`rest-restaurant/postman/REST-Restaurant.postman_collection.json`.
