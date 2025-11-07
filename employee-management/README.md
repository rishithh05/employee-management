##Employee Services API 

### Overview  
The Employee Services API is a RESTful web application built using Spring Boot to manage employee information.  
It provides full **CRUD (Create, Read, Update, Delete)** functionality and advanced search options using:  
- JPA Specifications 
- HQL (JPQL)
- Native SQL Queries

The project follows clean REST design principles and uses H2 in-memory database for testing and demo purposes.

# Tech Stack  

- Spring Boot 3+ 
- Spring Data JPA 
- H2 Database 
- Spring Validation (Jakarta Validation)  
- JUnit & Mockito (for Testing) 
- Maven (Build Tool)
- Java 17 or above

##Setup & Run (Using Spring Initializer)

Step 1: Project Creation This project was generated using Spring Initializer with the following configuration: 

Project: Maven Project
Language: Java
Spring Boot:3.x
Java Version:
Dependencies: 21
- Spring Web  
- Spring Data JPA
- H2 Database
- Validation

Step 2: Build the Project Open the project in your IDE (Eclipse) by exporting from the downloads.

Step 3 : Project structure Build the structure according to the project requirements in src/main/java folder and after create required interfaces and class to run the project

Step 4: Run the application Right-click "EmployeeServiceApplication" Select Run As → Java Application.

Step 5: Access Application

**Base URL: http://localhost:8080/api/employees**

**H2 Console: http://localhost:8080/h2-console**
- JDBC URL → jdbc:h2:mem:employeeDB
- Username → sa
- Password → (leave blank)

### API Endpoints Overview

POST → /api/employees
Create a new employee

GET → /api/employees
Fetch all employees

GET → /api/employees/email/{email}
Fetch employee by email

GET → /api/employees/name/{name}
Fetch employee(s) by name

PUT → /api/employees/update/{email}
Update employee’s last name, phone, and address

PATCH → /api/employees/update-phone/{email}?phone=
Update only the phone number

DELETE → /api/employees/{email}
Delete an employee by email

GET → /api/employees/search/spec?email=&name=
Search using JPA Specification

GET → /api/employees/search/hql?email=&name=
Search using JPQL (HQL)

GET → /api/employees/search/native?email=&name=
Search using Native SQL

 Postman Collection: 
All the above endpoints are tested and exported in the collection file:  
"Employee Services API.postman_collection.json"




