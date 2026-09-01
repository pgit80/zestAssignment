# Zest API — Product & Item Management System

A RESTful web application engineered with Spring Boot, Spring Security (JWT-based), JPA/Hibernate, and MySQL. The system provides nested CRUD operations, role-based authorization, automated token rotation, comprehensive unit/integration test coverage, and containerization with Docker.

---

## Architecture Overview

The system follows a multi-tier, modular architecture designed around separation of concerns:
[ Client / Postman / Swagger UI ]
│
▼
[ JwtFilter / SecurityContext ] ──(Validates Bearer Token)
│
▼
[ Controller Layer ] ────────(Handles HTTP Requests & DTO Mapping)
│
▼
[ Service Layer ] ─────────(Business Logic, BCrypt, Token Generation)
│
▼
[ DAO / JPA ] ───────────(Data Access & Query Execution)
│
▼
[ MySQL / H2 Database ] ──────(Relational Storage)

### Architectural Highlights
* **Stateless JWT Security:** Authentication is completely stateless. Every incoming request is intercepted by `JwtFilter` to populate the `SecurityContext`.
* **Refresh Token Rotation:** Access tokens are short-lived (15 mins), and refresh tokens (7 days) are rotated on each reissue request.
* **Cascading Relationships:** `ProductEntity` maintains a `@OneToMany` cascade relationship with `ItemEntity`, ensuring nested lifecycle management and integrity constraints.

---

## Tech Stack

| Domain | Technology |
|---|---|
| **Language** | Java 21 |
| **Framework** | Spring Boot 3.x |
| **Security** | Spring Security, JJWT (Java JWT) |
| **Persistence** | Spring Data JPA, Hibernate, MySQL 8.0, H2 (In-Memory Testing) |
| **Testing** | JUnit 5, Mockito, Spring Boot Test, MockMvc |
| **Documentation** | OpenAPI 3 / Swagger UI (`springdoc-openapi`) |
| **Containerization** | Docker, Docker Compose, Docker Hub (`pgit80/zest-api`) |
| **Build Tool** | Apache Maven |

---

## API Endpoint Details

### 1. Authentication Endpoints (`/api/v1/auth`)

| Method | Endpoint | Access | Description |
|---|---|---|---|
| `POST` | `/api/v1/auth/register` | Public | Register a new user with BCrypt-hashed password |
| `POST` | `/api/v1/auth/login` | Public | Authenticate user and receive access + refresh tokens |
| `POST` | `/api/v1/auth/refresh` | Public | Issue a new access/refresh token pair using valid refresh token |

### 2. Product Management (`/api/v1/products`)

| Method | Endpoint | Access | Description |
|---|---|---|---|
| `GET` | `/api/v1/products` | Authenticated | Retrieve all products along with nested items |
| `GET` | `/api/v1/products/{id}` | Authenticated | Fetch a specific product by ID |
| `POST` | `/api/v1/products` | Authenticated | Create a new product |
| `PUT` | `/api/v1/products/{id}` | Authenticated | Update an existing product |
| `DELETE` | `/api/v1/products/{id}` | Authenticated | Delete a product and cascade delete linked items |

### 3. Nested Item Management (`/api/v1/products/{productId}/items`)

| Method | Endpoint | Access | Description |
|---|---|---|---|
| `GET` | `/api/v1/products/{productId}/items` | Authenticated | Get all items associated with a product |
| `POST` | `/api/v1/products/{productId}/items` | Authenticated | Add a new item under a specific product |
| `DELETE` | `/api/v1/items/{itemId}` | Authenticated | Remove an item by ID |

---

## Setup & Running with Docker

### Prerequisites
* Docker Desktop installed and running.
* Git installed.

### Option A: Run using Pre-built Image from Docker Hub

1. Clone the repository:
   ```bash
   git clone [https://github.com/pgit80/zestAssignment.git](https://github.com/pgit80/zestAssignment.git)
   cd zestAssignment ```
2. Pull from the docker Image:
   ``` bash
   docker pull pgit80/zest-app:v1 ```
