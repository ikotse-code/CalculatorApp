# CalculatorApp Project

This project is a salary-based monthly benefit calculator. It includes a **Spring Boot backend**, **React frontend** and **Docker setup** for backend. 

Accessibility is supported for visually impaired users.

---

## Features
- Calculates monthly benefits based on gross salary and child's birthdate.
- Minimum monthly salary: €886, Maximum: €4000.
- Monetary values are rounded to 2 decimal places.
- Returns total benefit and breakdown over 12 months.
- Session is tracked via a unique `sessionId`.
- Shareable session links.
- Implements a scheduled job that automatically deletes expired calculator sessions daily at midnight.
- Validation & Error Handling.
- Mobile-friendly responsive UI with cards.
- Animated card appearance.
- Accessible for screen readers.


## Technologies & Frameworks

- **Java 21**
- **Spring Boot 3.5**
- **Spring Data JPA** 
- **SQLite (via JDBC)** 
- **Jakarta Validation** 
- **Lombok**
- **Hibernate Community Dialects**
- **React**
- **Node.js**
- **Docker**

## Project Structure

```text
CalculatorApp/
├─ backend/       # Spring Boot + Maven
│  ├─ src/main/java/...
│  │  ├─ config
│  │  ├─ controller
│  │  ├─ domain
│  │  ├─ dto
│  │  ├─ entity
│  │  ├─ exception
│  │  ├─ job
│  │  ├─ repository
│  │  ├─ service
│  │  ├─ util
│  │  └─ BackendApplication
│  ├─ src/main/resources
│  │  └─ application.yaml
│  ├─ Dockerfile
│  ├─ ...
│  └─ pom.xml
├─ frontend/      # React
│  ├─ public/
│  ├─ src/
│  │  ├─ api/
│  │  ├─ components/
│  │  ├─ hooks/
│  │  ├─ styles/
│  │  ├─ utils/
│  │  ├─ App.jsx
│  │  ├─ index.js
│  │  └─ ...
│  ├─ package.json
│  └─ ...
├─ data/
│  └─ calculator.db  # SQLite
├─ logs/
├─ ...
├─ docker-compose.yml
├─ README.md
└─ RUN.sh
```

## Database & Logs
- **Database**: SQLite database file is stored in the `data/` folder at the project root (`data/calculator.db`).
- **Logs**: Application logs are stored in the `logs/` folder at the project root (`backend.log`).

## Prerequisites
Before running the project, make sure you have installed:
- **Docker Desktop** (for backend container)
- **Node.js** (for running the frontend)

## Docker Support
- **Backend Dockerfile**: Located in backend/Dockerfile – used to build the Spring Boot backend container.
- **docker-compose.yml**: Located at the project root – can start backend service with a single command:
```bash
docker-compose up --build
```
- **Prerequisite**: Docker Desktop must be running.

## Running the Project

Clone the repository from GitHub:
```bash
git clone https://github.com/ikotse-code/CalculatorApp.git
```
Navigate to the project directory:
```bash
cd calculatorapp
```
If the repository already exists locally, update it:
```bash
git pull
```
Run the application:

On Linux / macOS:
```bash
chmod +x run.sh
./run.sh
```
On Windows (Git Bash / WSL):
```bash
bash run.sh
```
Access the backend API:
```
http://localhost:8080
```
Access the frontend UI:
```
http://localhost:3000
```

## API Endpoints
The backend provides REST endpoints documented with **Swagger**, accessible at:

http://localhost:8080/swagger-ui/index.html

POST `/api/calculator/session` – Creates a new session and returns the session ID. 

POST `/api/calculator/calculate` – Performs a calculation based on the request data and returns the result; returns `400 Bad Request` if the input is invalid.

GET `/api/calculator/restore/{sessionId}` – Restores a previously saved session by its ID; returns `404 Not Found` if the session does not exist.