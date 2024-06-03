# User CRUD Application with Spring Boot

This is a simple CRUD (Create, Read, Update, Delete) application for managing users, built with Spring Boot.

## Features

- Create a new user
- Retrieve all users
- Retrieve a user by ID
- Update an existing user
- Delete a user

## Technologies Used
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- H2 Database (for development and testing)
- Maven

## Tests
The application includes unit tests for testing the functionality of the UserController and UserService classes. These tests are written using JUnit and Mockito frameworks and cover various scenarios for CRUD operations on users.

UserControllerTest: Contains unit tests for testing the UserController class, which exposes RESTful endpoints for managing users.
UserServiceTest: Contains unit tests for testing the UserService class, which contains the business logic for managing users.
The tests ensure that the application functions as expected by simulating HTTP requests and verifying the responses and interactions with the underlying services.
