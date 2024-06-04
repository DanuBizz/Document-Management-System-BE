# Use OpenJDK 17 as base image
FROM maven:3.8.4-openjdk-17-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project object model (POM) file to the container
COPY pom.xml .

# Copy the entire project to the container
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Expose the port the app runs on
EXPOSE 8080

# Define the command to run the application when the container starts
CMD ["java", "-jar", "target/documentManagementService-0.0.1-SNAPSHOT.jar"]