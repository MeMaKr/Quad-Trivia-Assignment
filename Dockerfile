# Use OpenJDK 21 base image
FROM openjdk:21-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the Maven pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the app (skip tests for faster build)
RUN ./mvnw package -DskipTests

# Expose the port the Spring Boot app will run on
EXPOSE 8080

# Run the Spring Boot jar
CMD ["java", "-jar", "target/yourappname-0.0.1-SNAPSHOT.jar"]
