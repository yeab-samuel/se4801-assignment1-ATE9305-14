# ShopWave - Product Management REST API

**Name:** Yeab Samuel  
**Student Number:** ATE/9305/14  
**Module:** SE 4801 – Enterprise Application Development

## Tech Stack
- Java 21, Spring Boot 3.4, Maven
- Spring Data JPA, H2 (in-memory), Lombok
- Spring Boot Actuator, Bean Validation

## How to Build and Run
```bash
./mvnw spring-boot:run
```
App starts on http://localhost:8080. Sample data (products and categories) is loaded automatically on startup via DataLoader.

## API Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/products | List all products (paginated) |
| GET | /api/products/{id} | Get product by ID |
| POST | /api/products | Create a product |
| GET | /api/products/search?keyword=&maxPrice= | Search products |
| PATCH | /api/products/{id}/stock | Update stock |

## Running Tests
```bash
./mvnw test
```

## H2 Console
http://localhost:8080/h2-console  
JDBC URL: `jdbc:h2:mem:shopwavedb`

## AI Disclosure
This project was developed with AI assistance (Claude by Anthropic) for code structure guidance and debugging. All code was reviewed, understood, and verified by the student.
```
