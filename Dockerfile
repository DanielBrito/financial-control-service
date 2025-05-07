# Use Amazon Corretto image
FROM amazoncorretto:21.0.0

# Add metadata to the project
LABEL daniel.email="daniel@polymatus.com"

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY build/libs/financialcontrolservice-0.0.1.jar financialcontrolservice.jar

# Run the JAR file
ENTRYPOINT ["java","-jar","financialcontrolservice.jar"]
