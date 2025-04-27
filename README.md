# DCN2 Back-End Application Programming

## Overview

This project is a modernized back-end application built using the Spring Framework. It was developed as part of an academic performance assessment at Western Governors University. The application serves as the back-end for a vacation bookings system used by a travel agency. It replaces a legacy back-end by leveraging Spring Boot, Spring Data JPA, and RESTful web services while connecting to an existing MySQL database.

## Key Features

- **Spring Boot Setup**  
  Created using Spring Initializr with the following dependencies:
  - Spring Data JPA
  - Spring Boot Starter Data REST
  - MySQL Connector/J
  - Lombok

- **Layered Package Structure**  
  The project is organized into distinct packages to separate concerns:
  - **controllers:** Contains REST controllers including the checkout controller with a post mapping to place orders.
  - **entities:** Houses entity classes and enums as per the provided UML diagrams.
  - **dao:** Implements repository interfaces extending `JpaRepository` with added cross-origin support.
  - **services:** Provides business logic including checkout service interfaces and implementations for handling vacation orders.
  - **config:** Contains configuration classes such as the modified `RestDataConfig.java` and application properties.

- **Integration with MySQL**  
  Uses an existing MySQL database by importing the provided `application.properties` for connection configuration.

- **Validation & Cross-Origin Support**  
  Includes validation logic to enforce inputs from the Angular front-end and enables cross-origin resource sharing for API calls.

- **Sample Data Initialization**  
  Programmatically adds five sample customers ensuring the data is not overwritten on subsequent runs.

- **Version Control**  
  The project was continuously committed and pushed to GitLab, maintaining a detailed branch history with commit messages that reflect incremental changes based on the project tasks.

## Getting Started

### Prerequisites

- Java (JDK 11 or later)
- Maven or Gradle
- MySQL database instance
- IntelliJ IDEA (recommended for Spring projects)

### Installation and Execution

1. **Clone the Repository**
   ```bash
   git clone <repository-url>
   cd dcn2-back-end-application

2. Import the Project 
Open the project in IntelliJ IDEA ensuring all dependencies are resolved.

3. Configure the Database Copy the supplied application.properties into the resources folder if not already present. Verify your MySQL connection settings.

4. Build and Run the Application
In Terminal:
mvn spring-boot:run

5. Access the Application The REST API endpoints will be available at http://localhost:8080. Use tools like Postman or your Angular front-end to test the API.
