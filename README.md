# Structure

The project is a Maven project structured in 2 modules: backend and frontend.

The build script will copy the frontend compiled files inside the backend to serve them together inside the springboot 
application.

Also, the generated jar is executable on Ubuntu/CentOS systems.

The session is kept by using LocalStorage from client side. When a user registers, his/her id is saved inside the 
localStorage and if it is present the data is sent to the update endpoint instead the save endpoint.

## Backend
The backend is developed using java and the following tech stack:

* Springboot
* Hibernate
* Liquibase
* Junit
* DBUnit
* Lombok
* Swagger
* Dozer

At the boot, the application will run the liquibase scripts to create and populate the database with the initial data 
and will start the swagger endpoints, so that the users are able to use rest requests to explore the database.

SwaggerUI is available at http://url:port/swagger-ui.html

JPA and Hibernate were used to persist the data inside the database.

Dozer was chosen as mapper, for the mappings between DTOs and Entities.

## Frontend

The frontend is built using Angular2 framework and Bootstrap library for the UI style.

Building the hierarchical select was the hardest part, due to the recursive nature of the procedure to generate it 
from the data.

# Database

The choosen database is H2 in file mode, the application will create the database file inside the user's home directory.

This is the chosen structure for the database:

![DatabaseDiagram](diagram.png?raw=true "Database Diagram")

The tables DATABASECHANGELOG and DATABASECHANGELOGLOCK are created automatically by Liquibase and are not part of the 
application domain.

It has been chosen a hierarchical structure for the sectors, so that it is possible from UI side to build components 
with more flexibility. Also inserting new data is easy.

The user has only 3 fields (ID, NAME, TERMS_AGREED) even though TERMS_AGREED could be removed since no user is allowed to 
be registered without having accepted the terms. This could be checked from endpoint side, with some validation.


# Improvements

A lot of tests are missing, like service tests and domain tests.
The groovy library Spock could be used in order to generate more tests quickly.

The UI part has no tests at all and logging is missing.

# Build

To build the project is necessary to have NPM and Node installed.

1. Move inside the `frontend` folder and run `npm install`
2. Run `npm run build`
3. This generates the `dist` folder inside the `frontend` project, that will be automatically copied by Maven inside 
the `backend` project.
4. Inside the `backend` folder, run mvn package to create the jar file
5. Inside the `target` folder the file `backend-0.0.1-SNAPSHOT.jar` is generated and can be launched with the command
`./backend-0.0.1-SNAPSHOT.jar` on Ubuntu/CentOS systems, or `java -jar backend-0.0.1-SNAPSHOT.jar` on others 

