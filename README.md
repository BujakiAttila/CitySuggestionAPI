# City Suggestion API

[![Build Status](https://travis-ci.org/BujakiAttila/CitySuggestionAPI.svg?branch=master)](https://travis-ci.org/BujakiAttila/CitySuggestionAPI)

### Overview
The City Suggestion API application is simple Spring Boot application implementing a RESTful webservice providing city name suggestions based on a query string.

For further details about the API, please see the [API portal](https://www.apimatic.io/apidocs/citysuggestionapi)

### Running the application
The application can be started with maven, just use the `mvn spring-boot:run` command:

![starting the application with maven](https://github.com/BujakiAttila/CitySuggestionAPI/blob/master/doc/starting.gif?raw=true)

### Using the service

The service provides an endpoint under "/suggestions". City suggestion queries may be sent to this endpoint via HTTP GET. The query string with the partial name of the city must be passed in the "q" query parameter:

![example response in postman](https://github.com/BujakiAttila/CitySuggestionAPI/blob/master/doc/suggestions.gif?raw=true)

## Health endpoint 

The state of the application may be queries via the "health" endpoint under: `/actuator/health`.

This could be done for example with the following curl command:

`curl localhost:8080/actuator/health`

## API Documentation

The API documentation is also available in the application:

`http://localhost:8080/swagger-ui.html`

The user interface also provides "try-it-yourself" functionality: 

![API Documentation UI](https://github.com/BujakiAttila/CitySuggestionAPI/blob/master/doc/ApiDocumentation.gif?raw=true)

The [API portal](https://www.apimatic.io/apidocs/citysuggestionapi) hosted at apimatic.io can be used to generate clients as well.

The expected behaviour of the API is also captured by [postman based integration tests](https://github.com/BujakiAttila/CitySuggestionAPI/blob/master/doc/CitySuggestionAPI.postman_collection.json).

For details about the postman based integration tests, please see the [official postman documentation](https://learning.getpostman.com/docs/postman/scripts/test_scripts/).

![Postman test run](https://github.com/BujakiAttila/CitySuggestionAPI/blob/master/doc/PostmanTestRun.gif?raw=true)

## Startup and memory usage

On the reference system (Intel Core i7-8700@3.20GHz) the startup took 7 seconds.

The service needs 1 Gb memory. Based on the results of the stress tests, this is enough to get a smooth throughput for a load peeking at 7500 requests/s.

![Startup and Memory usage](https://github.com/BujakiAttila/CitySuggestionAPI/blob/master/doc/StartupMemoryUsage.jpg?raw=true)

## Load and stress testing

Based on the load and stress tests; the service is capable to handle traffic up to 7500 requests/s on the reference system.

Higher traffic leads to graceful degradation of the response times and the total throughput:

![Total response time chart](https://github.com/BujakiAttila/CitySuggestionAPI/blob/master/doc/TotalResponseTime.jpg?raw=true)

![Throughput chart](https://github.com/BujakiAttila/CitySuggestionAPI/blob/master/doc/RequestsPerSecond.jpg?raw=true)

For further details please see the [attached report](https://github.com/BujakiAttila/CitySuggestionAPI/blob/master/doc/StressTestResults.html).

### Source of city names
The data is "GeoNames Gazetteer" dataset.

For details see [the related readme file](http://download.geonames.org/export/dump/readme.txt) and the [GeoNames website](https://www.geonames.org/).
