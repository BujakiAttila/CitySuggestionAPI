# City Suggestion API

[![Build Status](https://travis-ci.org/BujakiAttila/CitySuggestionAPI.svg?branch=master)](https://travis-ci.org/BujakiAttila/CitySuggestionAPI)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=BujakiAttila_CitySuggestionAPI&metric=alert_status)](https://sonarcloud.io/dashboard?id=BujakiAttila_CitySuggestionAPI)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=BujakiAttila_CitySuggestionAPI&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=BujakiAttila_CitySuggestionAPI)


[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=BujakiAttila_CitySuggestionAPI&metric=coverage)](https://sonarcloud.io/dashboard?id=BujakiAttila_CitySuggestionAPI)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=BujakiAttila_CitySuggestionAPI&metric=sqale_index)](https://sonarcloud.io/dashboard?id=BujakiAttila_CitySuggestionAPI)

### Overview
The City Suggestion API application is a simple Spring Boot application, implementing a RESTful web service, providing city name suggestions based on a query string.

For further details about the API, please see the [API portal](https://www.apimatic.io/apidocs/citysuggestionapi)

### Preview

![Test](https://github.com/BujakiAttila/CitySuggestionAPI/blob/master/doc/Test.gif?raw=true)

### Running the application
The application can be started with [maven](https://maven.apache.org/), just use the `mvn spring-boot:run` command:

![starting the application with maven](https://github.com/BujakiAttila/CitySuggestionAPI/blob/master/doc/starting.gif?raw=true)

### Using the service

The service provides an HTTP endpoint under "/suggestions". City suggestion queries may be sent to this endpoint via HTTP GET. The query string with the partial name of the city must be passed in the "q" query parameter:

![example response in postman](https://github.com/BujakiAttila/CitySuggestionAPI/blob/master/doc/suggestions.gif?raw=true)

## Production Ready Features

The application utilizes the production ready features of the [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html) module.

Ths includes providing a [Health Endpoint](https://docs.microsoft.com/en-us/azure/architecture/patterns/health-endpoint-monitoring). The actual state of the application may be queried from the HTTP endpoint under: `/actuator/health`.

This could be done for example with the following [curl](https://curl.haxx.se/) command:

`curl localhost:8080/actuator/health`

## API Documentation

The API documentation is also available in the deployed application and can be queried runtime as well:

`http://localhost:8080/swagger-ui.html`

The web based [Swagger UI](https://swagger.io/tools/swagger-ui/) user interface also provides "try-it-yourself" functionality: 

![API Documentation UI](https://github.com/BujakiAttila/CitySuggestionAPI/blob/master/doc/ApiDocumentation.gif?raw=true)

The [API portal](https://www.apimatic.io/apidocs/citysuggestionapi) hosted at [apimatic.io](https://www.apimatic.io/) can be used to generate clients for different programming langauges as well (including Java, C#, Python and many others).

The expected behaviour of the API is also captured by [postman based integration tests](https://github.com/BujakiAttila/CitySuggestionAPI/blob/master/doc/CitySuggestionAPI.postman_collection.json).

These may be used for integration and system testing purposes.

For details about the postman based integration tests, please see the [official postman documentation](https://learning.getpostman.com/docs/postman/scripts/test_scripts/).

![Postman test run](https://github.com/BujakiAttila/CitySuggestionAPI/blob/master/doc/PostmanTestRun.gif?raw=true)

## Profiling: Startup and memory usage

On the reference system (Intel Core i7-8700@3.20GHz) the startup took 7 seconds. This may be [further optimized](https://stackoverflow.com/questions/35709234/minimise-spring-boot-startup-time), if needed.

The service needs 1 Gb memory. Based on the results of the stress tests, this is enough to get a smooth throughput for a load peeking at 7500 requests/s.

![Startup and Memory usage](https://github.com/BujakiAttila/CitySuggestionAPI/blob/master/doc/StartupMemoryUsage.jpg?raw=true)

## Load and stress testing

Based on the load and stress tests; the service is capable to handle traffic up to 7500 requests/s on the reference system.

Higher traffic leads to graceful degradation of the response times and the total throughput:

![Total response time chart](https://github.com/BujakiAttila/CitySuggestionAPI/blob/master/doc/TotalResponseTime.jpg?raw=true)

![Throughput chart](https://github.com/BujakiAttila/CitySuggestionAPI/blob/master/doc/RequestsPerSecond.jpg?raw=true)

If you need to serve higher load, then please consider using several instances of the service using [load balancing](http://nginx.org/en/docs/http/load_balancing.html) or even [configure auto scaling](https://docs.aws.amazon.com/autoscaling/ec2/userguide/autoscaling-load-balancer.html), especially if [dynamic scaling](https://aws.amazon.com/ec2/autoscaling/) capatibilities are needed as the load increases.

For further details about the load and stress testing please see the [attached report](https://github.com/BujakiAttila/CitySuggestionAPI/blob/master/doc/StressTestResults.html).

### Source of city names
The data is ["GeoNames Gazetteer"](https://www.geonames.org/about.html) dataset. (Licensed under [CC Attribution License](https://creativecommons.org/licenses/by/4.0/legalcode))

For details see [the related readme file](http://download.geonames.org/export/dump/readme.txt) and the [GeoNames website](https://www.geonames.org/). 


