# Containerized Distributed Quotation Services using RMI

## Description:

In this project, I implemented the containerization of a multi-module distributed system using RMI (Remote Method Invocation). The system comprises multiple services, including three quotation services, a broker, and a client, all of which were previously developed and tested individually. The final task involved converting these services into Docker images and deploying them using a docker-compose configuration. This project demonstrates my ability to work with RMI-based distributed systems, Maven multi-module projects, and containerization using Docker.

## Features

1. **Distributed Quotation Services:** Three quotation services (Auldfellas, DodgyGeezers, and GirlsAllowed) implemented as RMI-based distributed objects.
2. **Broker Service:** Acts as the mediator between clients and quotation services.
3. **Client Service:** Requests quotations from the broker and prints the received results.
4. **Containerized Microservices:** Each service is containerized using Docker, enabling isolated and independent deployment.
5. **Docker Compose:** Manages the setup and orchestration of the services, ensuring smooth communication between the client, broker, and quotation services.

## Technologies Used

* **Java RMI:** For creating remote objects and enabling communication between distributed services.
* **Maven:** To manage the multi-module Java project.
* **Docker:** For containerizing services to run in isolated environments.
* **Docker Compose:** For orchestrating multi-container Docker applications.
* **JUnit:** For unit testing RMI services.

## Project Structure
The project is divided into the following services:

* **Auldfellas Quotation Service (auldfellas/):** Provides quotes for older customers.
* **DodgyGeezers Quotation Service (dodgygeezers/):** Provides quotes for high-risk customers.
* **GirlsAllowed Quotation Service (girlsallowed/):** Provides quotes for female customers.
* **Broker Service (broker/):** Manages communication between the client and quotation services.
* **Client Service (client/):** Requests quotes from the broker.

Each service is packaged as a separate Docker image.

## Installation Instructions

### Clone the repository

* git clone https://github.com/your-username/distributed-rmi-quotation-system.git
* cd distributed-rmi-quotation-system

### Build and Compose the Docker images:

_Make sure Docker is installed on your system._ 

1. Clean install the project. 

3. Build and compose the Docker images for each service

* mvn clean install

* docker compose up --build

_This will start the quotation services, the broker, and the client in separate containers._

### Stop the system

* To stop the running services, run:
* docker-compose down

#### Running the Services Individually:

Each service can be run individually by navigating to its directory and running the respective Dockerfile. 

Here is an example for running the Auldfellas service:

* cd auldfellas
* docker build -t auldfellas-service .
* docker run auldfellas-service

Repeat the same steps for other services.

### Testing:

Unit tests have been written using JUnit to ensure that the quotation services are functioning correctly. 

The tests are located in the src/test directory of each service. You can run the tests using Maven:

* mvn test

# Future Improvements:

* Add support for more quotation services.
* Implement advanced fault tolerance mechanisms.
* Refactor the client to request services based on configurable parameters.

# Contact:
For any queries, feel free to reach out at akash.saini@ucdconnect.ie