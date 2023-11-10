# Use a base image with Java 11 installed
FROM openjdk:11-jre-slim

# Set the working directory to /app
WORKDIR /app

# Copy the Spring Boot application JAR file to the container
COPY target/my-spring-boot-app.jar /app

# Expose the port on which the Spring Boot application is running
EXPOSE 8080

# Set the entry point for the Docker container to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "my-spring-boot-app.jar"]