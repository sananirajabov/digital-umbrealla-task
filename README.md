##Postgres set up

Before running Java application we need to install and configure Postgres and database. You can either download Postgres Server and PGAdmin from official website (https://www.postgresql.org/download/) or use Docker. If you want to use Docker, then run following commands below:

`docker run --name <CONTAINER_NAME> -e POSTGRES_PASSWORD=<PASSWORD> -d postgres`

The default password for created Postgres Server is -> `postgre`

After created Postgres server, then you will create a server and database in it, and database name should be `cbar`

That is all for database set up.

*Note*: You don't need to create any table. Liquibase will handle table creation.

##Spring Boot Application set up

To install required dependencies and run unit tests

`mvn clean install`

To running Spring Boot application you can use either IDE running tool or you can use this command below:

`mvn spring-boot:run`

To see API information on Spring Fox (Swagger), go to the following URL. This is for local environment, in production host name should be changed: 

http://localhost:8080/swagger-ui/index.html

On there, you will see APIs and their needed request and response types.

##Authentication

The default credentials for log in are:

`
{
    "username": "admin",
    "password": "admin"
}
`

After authenticated successfully, you will get JWT on the response. Please, use that JWT on every request and choose 
`Bearer` Authorization Type.
