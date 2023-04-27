# Bookstore Application

This is a simple application for managing books in a bookstore. It allows users to perform CRUD operations on a Book entity, including adding, deleting, and searching for books.

## Technologies Used
- Java 11
- Spring Boot 2.5.4
- MySQL 8.0.25
- Maven 3.8.2
- Swagger 2

## Getting Started
To get started with the project, follow these steps:

1. Clone the repository to your local machine.
2. Open the project in your preferred IDE (e.g. IntelliJ IDEA, Eclipse).
3. Configure the MySQL database settings in the `application.properties` file located in the `src/main/resources` directory.
4. Build the project using Maven by running `mvn clean install` from the command line.
5. Run the project using your IDE or by running the generated jar file from the command line: `java -jar target/bookstore-0.0.1-SNAPSHOT.jar`

## API Documentation
This application uses Swagger to document its API. Once the application is running, navigate to http://localhost:8080/swagger-ui.html in your web browser to access the Swagger documentation.

## Endpoints

### POST /books
Creates a new book in the database.

### DELETE /books/{id}
Deletes a book from the database by ID.

### GET /books/{id}
Retrieves a book from the database by ID.

### GET /books/title/{title}
Retrieves a list of books from the database that match the given title.

### GET /books/category/{category}
Retrieves a list of books from the database that belong to the given category.

### GET /books/author/{author}
Retrieves a list of books from the database that were authored by the given author.

### GET /books
Retrieves a list of all books from the database.

## Models

### Book
Represents a book in the bookstore.

| Field    | Type         | Constraints            |
|----------|--------------|------------------------|
| id       | Long         | Auto-generated         |
| title    | String       | Required, max 50 chars |
| author   | String       | Required, max 50 chars |
| category | Enum (string)| Required               |
| price    | Double       | Required, non-negative |

### Category
Represents a book category.

| Value           |
|-----------------|
| Technical       |
| Literature      |
| Humour          |
| Poetry          |
| Science_friction|


## Classes
### BookController
This class represents the REST API endpoints for Book operations, such as adding, deleting, searching for books, etc.

### BookService
This class represents the business logic for Book operations, such as adding, deleting, searching for books, etc.

### BookRepository
This interface represents the data access layer for Book operations. It extends the JpaRepository interface provided by Spring Data JPA, which provides several built-in methods for handling database operations.
