# Assumptions and general notes

## APR related calculations
The mathematical calculations performed according to formula documented [here](https://www.handbook.fca.org.uk/handbook/MCOB/10/3.html)

>TODO: Calculate interest rate for multiple installments

## Security
This functionality is not built in here. It can be implemented:
- in a layer that is in front of the service ecosystem (like Apigee)
- it can assume API key management approach with appropriate permissions implemented and annotated in the code
- the API could be open
- the security strategy could be different depending on the environment the API is served from (test vs production)

## Tests
The unit tests are not quite sophisticated. They have been put there to demonstrate the test drive development approach.

>TODO: Integrated tests implemented as postman collections. This could be later transformed to automate integration testing
>as part of deployment process.

## Configuration files
- Current database is set to HA for unit testing purposes only
- Password to the database instance is currently included in the docker-compose.yml; this is only for testing; in production environment this type of information should be kept outside of the source code base

>TODO:
>- Add maven profile based communication to enable different types of databases for unit testing, integration testing and target environment deployement
>- Add liquibase database definition to enable versioning of the DB schema

## Container
The application is packaged into a docker container.
For testing purposes in a local environment, docker-compose configuration has been added with two elements:
- database (PostgreSQL relational DB has been assumed)
- the application
This will allow easy local integration testing
