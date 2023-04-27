# Bookstore Application

This is a sample application for a bookstore. It provides RESTful endpoints to perform CRUD operations on books, including adding a new book, deleting a book, retrieving a book by ID, searching books by title, retrieving books by category, and retrieving all books. The application is built using Spring Boot, and uses a MySQL database to store the books.

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

## API Exploring
This application uses Swagger generates a user-friendly UI for interacting with the API. Once the application is running, navigate to http://localhost:8080/swagger-ui.html in your web browser for exploring and testing RESTful APIs.

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
