# **Java Backend Developer Hiring Assignment** 

### **_Official Technical Evaluation Document_** 

Prepared By: Zest India IT Pvt Ltd 

## **1. Problem Statement** 

Design a RESTful API solution around Products to perform full CRUD operations using Java and Spring Boot. 

## **2. Technical Stack Requirements** 

- Java 17+ 

- Spring Boot 

- Spring Data JPA (Hibernate) 

- PostgreSQL or MySQL 

- Spring Security with JWT & Refresh Token 

- JUnit 5 & Mockito 

- Swagger/OpenAPI Documentation 

- Docker & Docker Compose 

## **3. API Design Expectations** 

- Resource-oriented RESTful design. 

- Consistent URL structure with API versioning (/api/v1/). 

- JSON request and response format. 

- Standardized error handling response. 

- Pagination support for collection endpoints. 

## **4. Endpoint Structure (Sample)** 

```
GET     /api/v1/products
GET     /api/v1/products/{id}
POST    /api/v1/products
PUT     /api/v1/products/{id}
DELETE  /api/v1/products/{id}
GET     /api/v1/products/{id}/items
```

## **5. Database Structure** 

```
CREATE TABLE product (
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(255) NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    created_on TIMESTAMP NOT NULL,
    modified_by VARCHAR(100),
```

```
    modified_on TIMESTAMP
);
```

```
CREATE TABLE item (
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(id)
);
```

## **6. Security & Performance Requirements** 

- JWT authentication with refresh token rotation. 

- Role-based authorization. 

- Input validation using Jakarta Validation. 

- Database indexing strategy. 

- Async processing where applicable. 

- CORS configuration and HTTPS enforcement. 

## **7. Testing Requirements** 

- Unit testing using JUnit 5 and Mockito. 

- Integration testing using Spring Boot Test. 

- Use H2 in-memory database for tests. 

- Adequate test coverage for services and controllers. 

## **8. GitHub Submission Instructions** 

- Create a new PUBLIC GitHub repository in your account. 

- Push complete source code to that repository. 

- Ensure README.md contains setup instructions and architecture explanation. 

- • Include Dockerfile and docker-compose.yml. 

- Submit only the GitHub repository URL in the Google Form. 

- Do NOT share ZIP files. Only GitHub repository URL is accepted. 

## **9. Google Form Submission Details** 

After pushing the code to GitHub, submit the following details in the provided Google Form: 

- Full Name 

- Email Address 

- Mobile Number 

- Years of Experience 

- GitHub Public Repository URL 

- Time Taken to Complete Assignment 

## **10. Evaluation Criteria** 

- Code Structure & Clean Architecture 

- REST API Design Quality 

- Security Implementation 

- Testing Coverage 

- Documentation Quality 

- Docker & Deployment Readiness 

