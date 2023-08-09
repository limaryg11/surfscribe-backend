# Use an official OpenJDK runtime as a parent image
FROM openjdk:17

# Set the working directory
WORKDIR /surfscribe-backend

# Copy the Spring Boot jar into the container
COPY target/surfscribe-backend-app.jar surfscribe-backend-app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "surfscribe-backend-app.jar"]
