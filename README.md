# URL Shortener API

This project aims to demonstrate the use of Spring Boot 2 + Java 10 + Integration tests with in memory database H2

### Build
```
mvn clean package
```

### Execute
* Create a Mysql database and execute the SQL script schema-mysql.sql
* Update the datasource information on application.properties
* Run the following command (with an external configuration file):
	java -jar urlshortener-2.0.0.jar --spring.config.location=application.properties

 