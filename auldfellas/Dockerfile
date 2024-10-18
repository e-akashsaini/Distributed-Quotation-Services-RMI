# Step 1: Use an OpenJDK base image
FROM openjdk:22-jdk-slim

# Step 2: Copy the shaded JAR file for the broker into the container
COPY target/auldfellas-1.0-SNAPSHOT-allinone.jar /auldfellas.jar
ENV SERVER_HOST=broker

# Step 3: Run the broker service with a delay
# The delay is added to allow the RMI registry to initialize before the broker service runs
CMD sleep 5 && java -jar /auldfellas.jar $SERVER_HOST


