# About
This is a RESTful API that allows users to register and log in to the system, and perform CRUD operations on movies. The API uses Spring Security to secure the endpoints and authenticate users using JSON Web Tokens (JWT). It also utilizes Spring Data JPA for data access and persistence and it's intended to be used with PostgreSQL.

## Prerequisites
* Java 17 or higher
* Maven

## Installing
Clone the repository to your local machine:

```bash
git clone https://github.com/LeandroFernandezRuhl/spring-movie-api.git
```
Navigate to the project directory:
```bash
cd spring-movie-api
```

Run the following command to build the project:
```bash
mvn clean install
```

Run the following command to start the application:
```bash
java -jar target/spring-movie-api.jar
```

The application should now be running on http://localhost:8080.

## Usage
### Authentication
To use the API, you need to authenticate yourself first by obtaining a JWT token. To do this, send a POST request to /api/auth/login with your username and password in the request body:
```json
{
  "username": "yourusername",
  "password": "yourpassword"
}
```

If the credentials are valid, you will receive a JWT token in the response:
```json
{
  "token": "yourjwttoken"
}
```

Include the token in the Authorization header of subsequent requests to the API:
```makefile
{
  Authorization: Bearer yourjwttoken
}
```

## Movie Endpoints
### GET
* To retrieve all movies, send a GET request to /api/movies.
* To retrieve a specific movie, send a GET request to /api/movies/{id}, where {id} is the ID of the movie.
* To retrieve the total count of movies, send a GET request to /api/movies/count.

### POST
* To create a new movie, send a POST request to /api/movies with the movie details in the request body:
```json
{
  "title": "The Shawshank Redemption",
  "director": "Frank Darabont",
  "genre": "Drama",
  "rating": 9.3,
  "duration": 8520,
  "year": 1994,
  "releaseDate": "1994-09-23",
  "available": true
}
```

### PUT
* To update an existing movie, send a PUT request to /api/movies/{id} with the updated movie details in the request body.

### DELETE
* To delete a specific movie, send a DELETE request to /api/movies/{id}, where {id} is the ID of the movie.

* To delete multiple movies at once, send a POST request to /api/movies/deleteMany with a list of movie IDs in the request body:
```makefile
{
  "ids": [1, 2, 3]
}
```

## User Endpoints
### REGISTER
To register a new user, send a POST request to /api/auth/register with the user details in the request body:
```json
{
  "username": "newuser",
  "email": "newuser@example.com",
  "password": "newpassword"
}
```
### LOGIN
To login, send a POST request to /api/auth/login with the user details in the request body:
```json
{
  "username": "user",
  "password": "password"
}
```

## Additional Information
For a full list of endpoints and their usage, please refer to the Swagger UI documentation available at http://localhost:8080/swagger-ui.html once you're running the application.

## Built With
* Spring Boot
* Spring Web
* Spring Security
* Spring Data JPA
* Lombok
* JJWT API
