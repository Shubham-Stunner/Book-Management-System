# Java Book Management (3-Tier) — Spring Boot + MongoDB

## Structure
- frontend/ : Spring Boot MVC + Thymeleaf (port 8080)
- backend/  : Spring Boot REST API + Spring Data MongoDB (port 8081)
- database/ : MongoDB init script (optional seed)

## Prereqs
- JDK 17+, Maven 3.9+
- Docker & Docker Compose

## Build
```bash
mvn -q -DskipTests clean package
```

## Run (Docker Compose)

```bash
docker compose up --build
# Web:   http://localhost:8080
# API:   http://localhost:8081/api/books
```

## Run locally without Docker

1. Start Mongo:

```bash
docker run -p 27017:27017 -e MONGO_INITDB_DATABASE=booksdb --name mongo -d mongo:6
```

2. Start backend:

```bash
cd backend
export MONGODB_URI=mongodb://localhost:27017/booksdb
mvn spring-boot:run
```

3. Start frontend:

```bash
cd ../frontend
export API_BASE=http://localhost:8081
mvn spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080)
