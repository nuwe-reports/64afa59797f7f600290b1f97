# Accenture Tech Hub - Java Back End Developer Challenge

This project represents a challenge conducted by [Nuwe](https://nuwe.io/) in partnership with [Accenture](https://www.accenture.com/). The primary objective is to modernize the appointment system of a hospital.

My responsibility entails finalizing the implementation and development of the backend's appointment management system. The following tasks have been addressed:

1. To enable the creation of appointments via the API, the `AppointmentController.java` file has been implemented.

2. Tests have been conducted for entities using the `EntityUnitTest.java` and `EntityControllerUnitTest.java` files.

3. The API has been deployed in a scalable manner, with distinct Dockerfiles for the MySQL database (`Dockerfile.mysql`) and the microservice (`Dockerfile.maven`).

4. Additionally, a `docker-compose.yml` file has been generated to simplify the deployment process of the API.

## Guidelines for Running the Project Using Docker
1. If not already installed, set up [Docker](https://www.docker.com/) on your system by following this [Download Link](https://www.docker.com/products/docker-desktop/).

2. Clone the project repository from the provided source.

3. Use the command line or terminal to navigate to the project directory.

4. Now, decide on your preferred approach for bringing the API to life: the "Easy Way."

5. Execute the following command: `docker compose up`

Confirm that both the MySQL container and the microservice container are up and running by using the command: `docker ps`.

The application should now be accessible on port 8080 on your local machine (http://localhost:8080/).

