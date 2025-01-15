# Stage 1: Use a base image with Java runtime
FROM openjdk:17-jdk

# Stage 2: Set the working directory inside the container
WORKDIR /app

# Stage 3: Copy the jar file from the local machine to the working directory in the container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Stage 4: Expose the port the application will run on
EXPOSE 8081

# Stage 5: Specify the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
