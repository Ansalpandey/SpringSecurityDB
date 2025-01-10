# Stage 1: Build Stage
FROM gradle:8.4-jdk21 AS builder

# Set the working directory
WORKDIR /app

# Copy the Gradle project files
COPY . .

# Build the application and create a fat JAR
RUN ./gradlew clean build -x test

# Stage 2: Runtime Stage
FROM eclipse-temurin:21-jre-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy only the built JAR file from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
