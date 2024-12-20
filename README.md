# PuppyGram 🐕 🐈

A simple API for a WhateverGram made for puppies.

## Features

- User registration and login
- User profile with post and like statistics
- Authentication and authorization using Spring Security
- Posts with like functionality
- Integration with PostgreSQL database

## Technologies Used

- Java
- Spring Boot
- Spring Security
- PostgreSQL
- Docker
- Maven

## Getting Started

### Prerequisites

- Java 22
- Maven
- Docker

### Running the Application

#### Running Locally

1. Clone the repository:
    ```sh
    git clone https://github.com/eaglequeiroz/puppygram.git
    cd puppygram
    ```

2. Build the project:
    ```sh
    mvn clean install -DskipTests=true
    ```

3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

Observation: The application will run on port 8080 by default and you must have or a local instance of MongoDB and PostgreSQL, or their docker images up and running.
4. Access the service on [http://localhost:8080](http://localhost:8080)

#### Running with Docker

1. Clone the repository:
    ```sh
    git clone https://github.com/eaglequeiroz/puppygram.git
    cd puppygram
    ```

2. Build and start the service:
    ```sh
    docker-compose up --build
    ```

3. Access the service on [http://localhost:8080](http://localhost:8080)

## API Endpoints

### User

- `POST /user/register` - Register a new user
- `POST /user/login` - Login a user
- `GET /user/{userId}/profile` - Get user profile (requires authentication)

### Post

- `GET /posts` - Get all posts (requires authentication)
- `POST /posts` - Create a new post (requires authentication)
- `POST /posts/{postId}/like` - Like a post (requires authentication)

## Configuration

### Database

The application uses PostgreSQL as the database. Configure the database connection in `application.yml`:

```yaml
spring:
    datasource:  
        url: jdbc:postgresql://localhost:5432/puppygram
        username: yourusername
        password: yourpassword
    jpa:
        hibernate:
            ddl-auto: update
```

## Other information

For testing purposes, you can get the Postman collection in the resource folder so you could try all the endpoints without much to worry.


Regards,

     
Igor Queiroz 🐕 🐈