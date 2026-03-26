# ShopWave Starter — SE 4801 Assignment 1

**Student Name:** Yeab Samuel  
**Student Number:** ATE/9305/14  
**Course:** SE 4801 — Enterprise Application Development  
**Submission:** Week 4 · March 27, 2026

---

## Overview

ShopWave Starter is a Spring Boot 3.x RESTful web application built as part of the SE 4801 Enterprise Application Development assignment. It demonstrates a fully layered enterprise application architecture — Controller → Service → Repository → Database — with a complete product catalogue domain model, paginated REST endpoints, global exception handling, and an automated test suite.

The application uses an in-memory H2 database seeded with sample data on startup via a `DataLoader`, making it immediately runnable with zero external database configuration.

---

## Technology Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 21 (Temurin) | Language runtime |
| Spring Boot | 3.5.12 | Application framework |
| Spring Data JPA | 3.5.x | Data persistence layer |
| Hibernate | 6.6.x | ORM / DDL generation |
| H2 Database | 2.3.x | In-memory database (dev/test) |
| Lombok | 1.18.x | Boilerplate code reduction |
| Spring Boot Actuator | 3.5.x | Health and metrics endpoints |
| Spring Validation | 3.5.x | Request validation |
| JUnit 5 | 5.12.x | Unit and integration testing |
| Mockito | 5.x | Mocking framework |
| Maven | 3.9.x | Build and dependency management |

---

## Project Structure

```
shopwave-starter/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/shopwave/
│   │   │       ├── ShopwaveStarterApplication.java   ← Main entry point
│   │   │       ├── DataLoader.java                   ← Seeds DB with sample data on startup
│   │   │       ├── controller/
│   │   │       │   ├── ProductController.java        ← REST endpoints
│   │   │       │   └── GlobalExceptionHandler.java   ← @RestControllerAdvice
│   │   │       ├── dto/
│   │   │       │   ├── ProductDTO.java               ← Response record
│   │   │       │   ├── CreateProductRequest.java     ← Request record with validation
│   │   │       │   └── UpdateStockRequest.java       ← Stock delta record
│   │   │       ├── exception/
│   │   │       │   ├── ProductNotFoundException.java ← Custom RuntimeException
│   │   │       │   └── ErrorResponse.java            ← Standard error response record
│   │   │       ├── model/
│   │   │       │   ├── Category.java                 ← JPA entity
│   │   │       │   ├── Product.java                  ← JPA entity
│   │   │       │   ├── Order.java                    ← JPA entity with addItem() helper
│   │   │       │   ├── OrderItem.java                ← JPA entity
│   │   │       │   └── OrderStatus.java              ← Enum: PENDING/SHIPPED/DELIVERED/CANCELLED
│   │   │       ├── repository/
│   │   │       │   ├── ProductRepository.java        ← JpaRepository + derived queries
│   │   │       │   └── CategoryRepository.java       ← JpaRepository
│   │   │       └── service/
│   │   │           └── ProductService.java           ← Business logic + manual mapper
│   │   └── resources/
│   │       └── application.properties               ← App configuration
│   └── test/
│       └── java/com/shopwave/
│           ├── ProductControllerTest.java            ← @WebMvcTest
│           ├── ProductRepositoryTest.java            ← @DataJpaTest
│           ├── ProductServiceTest.java               ← Mockito unit test
│           └── ShopwaveStarterApplicationTests.java  ← Context load test
├── pom.xml
├── mvnw / mvnw.cmd
└── README.md
```

---

## Prerequisites

Before running this project, ensure the following are installed:

- **Java 21** — Temurin 21 recommended (install via `choco install temurin21`)
- **Maven** — included via Maven Wrapper (`./mvnw`), no separate installation needed
- **Git** — for cloning the repository

Verify Java version:
```bash
java -version
# Expected: openjdk version "21.x.x"
```

---

## How to Build

Clone the repository:
```bash
git clone https://github.com/yeab-samuel/se4801-assignment1-ATE9305-14.git
cd se4801-assignment1-ATE9305-14/shopwave-starter
```

Compile the project:
```bash
./mvnw clean compile
```

Expected output:
```
[INFO] BUILD SUCCESS
```

---

## How to Run

Start the application using Maven:
```bash
./mvnw spring-boot:run
```

Or alternatively, run from IntelliJ IDEA using the green ▶ button on `ShopwaveStarterApplication.java` (ensure Java 21 SDK is configured in Project Structure).

The application starts on **port 8080**. On startup you will see:
```
Started ShopwaveStarterApplication in X seconds
```

The `DataLoader` automatically inserts sample data:
- 1 Category: **Electronics**
- 3 Products: **Laptop Pro**, **Wireless Mouse**, **USB-C Hub**

---

## How to Run Tests

Run the full test suite:
```bash
./mvnw test
```

Expected output:
```
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

The test suite includes:

| Test Class | Type | Coverage |
|---|---|---|
| `ProductServiceTest` | Unit (Mockito) | createProduct happy path, ProductNotFoundException |
| `ProductControllerTest` | Web Layer (@WebMvcTest) | GET /api/products → 200, GET /api/products/999 → 404 |
| `ProductRepositoryTest` | JPA (@DataJpaTest + H2) | findByNameContainingIgnoreCase |
| `ShopwaveStarterApplicationTests` | Integration | Full context loads successfully |

---

## API Endpoints

Base URL: `http://localhost:8080`

| Method | Endpoint | Description | Response |
|---|---|---|---|
| `GET` | `/api/products` | Get all products (paginated) | `200 Page<ProductDTO>` |
| `GET` | `/api/products/{id}` | Get product by ID | `200 ProductDTO` / `404` |
| `POST` | `/api/products` | Create a new product | `201 ProductDTO` |
| `GET` | `/api/products/search?keyword=&maxPrice=` | Search products | `200 List<ProductDTO>` |
| `PATCH` | `/api/products/{id}/stock` | Update stock level | `200` / `400` / `404` |
| `GET` | `/actuator` | Spring Boot Actuator health | `200` |
| `GET` | `/h2-console` | H2 in-memory database UI | Browser |

### Sample Requests (Postman or curl)

**GET all products:**
```
GET http://localhost:8080/api/products
```

**GET single product:**
```
GET http://localhost:8080/api/products/1
```

**POST create product:**
```
POST http://localhost:8080/api/products
Content-Type: application/json

{
  "name": "Mechanical Keyboard",
  "description": "Compact mechanical keyboard",
  "price": 89.99,
  "stock": 20
}
```

**PATCH update stock:**
```
PATCH http://localhost:8080/api/products/1/stock
Content-Type: application/json

{ "delta": -2 }
```

**GET search:**
```
GET http://localhost:8080/api/products/search?keyword=laptop
```

### Error Response Format

All errors return a consistent JSON structure:
```json
{
  "timestamp": "2026-03-26T07:35:17",
  "status": 404,
  "error": "Not Found",
  "message": "Product not found with id: 999",
  "path": "/api/products/999"
}
```

---

## H2 Database Console

While the application is running, access the database UI at:
```
http://localhost:8080/h2-console
```

Connection settings:
- **JDBC URL:** `jdbc:h2:mem:shopwavedb`
- **User Name:** `sa`
- **Password:** *(leave empty)*

---

## DataLoader

The `DataLoader.java` class (`@Configuration`) implements `CommandLineRunner` and automatically seeds the database with sample data every time the application starts. This makes the API immediately usable without manual data entry.

It inserts:
- The **Electronics** category
- **Laptop Pro** (£1,299.99, stock: 15, category: Electronics)
- **Wireless Mouse** (£29.99, stock: 50)
- **USB-C Hub** (£49.99, stock: 30)

Since the application uses `spring.jpa.hibernate.ddl-auto=create-drop`, the database is reset on every restart and the DataLoader re-seeds it fresh each time.

---

## Architecture

The application follows a strict 4-layer architecture:

```
HTTP Request
     ↓
 Controller      (@RestController)        — handles HTTP, delegates to service
     ↓
 Service         (@Service @Transactional) — business logic, validation, mapping
     ↓
 Repository      (@Repository)            — data access via Spring Data JPA
     ↓
 Database        (H2 in-memory)           — persistence
```

All read-only service methods are annotated with `@Transactional(readOnly = true)` for performance optimisation.

---

## AI Assistance Disclosure

Claude AI (Anthropic) was used as a development assistant throughout this assignment. The AI's involvement included explaining Java and Spring Boot concepts, guiding environment setup (Java 21 via Chocolatey, IntelliJ configuration), generating boilerplate code for entity classes, the service layer, repository interfaces, controller endpoints, and the DataLoader, as well as assisting with debugging issues such as Lombok annotation processing and duplicate bean definitions.

All generated code was reviewed, tested, and verified by the student. The student ran the full application, executed all 6 tests, and manually tested every API endpoint using both the browser and Postman to confirm correct behaviour. Conceptual understanding of each component was developed through the process of building, debugging, and verifying the application interactively throughout the assignment.

---

## GitHub Repository

```
https://github.com/yeab-samuel/se4801-assignment1-ATE9305-14
```

---

*SE 4801 — Enterprise Application Development · Addis Ababa University · Department of Software Engineering*