# Use Maven image with JDK 21 (matches your Spring Boot Java version)
FROM maven:3.9.5-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml and download dependencies first (caching benefit)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the application (skip tests for faster build)
RUN mvn package -DskipTests

# Run stage
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy the JAR built in previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java","-jar","app.jar"]
