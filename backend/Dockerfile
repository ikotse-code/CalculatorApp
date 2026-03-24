# Build stage
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /build

COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .

RUN chmod +x mvnw

# Dependencies
RUN ./mvnw -B -q -e -DskipTests dependency:go-offline

COPY src ./src

RUN ./mvnw clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# ensure directories exist inside container
RUN mkdir -p /app/data /logs

COPY --from=build /build/target/*-SNAPSHOT.jar backend/app.jar

EXPOSE 8080

# start command (container startup command)
ENTRYPOINT ["java","-jar","backend/app.jar"]