# Use a base image with Java runtime
FROM adoptopenjdk/openjdk11:alpine-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container
COPY build/libs/*.jar app.jar

# Expose the port that your application listens on
EXPOSE 9000

# Specify the command to run your application
CMD ["java", "-jar", "app.jar"]
