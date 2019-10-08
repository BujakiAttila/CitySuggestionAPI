# City Suggestion API

### Overview
The City Suggestion API application is simple Spring Boot application implementing a RESTful webservice providing city name suggestions based on a query string.

For further details about the API, please see the [API portal](https://www.apimatic.io/apidocs/citysuggestionapi)



### Running the application
The application can be started with maven, just use the `mvn spring-boot:run` command:

![starting the application with maven](https://github.com/BujakiAttila/CitySuggestionAPI/blob/master/doc/starting.gif?raw=true)

### Using the service

The service provides an endpoint under "/suggestions". City suggestion queries may be sent to this endpoint via HTTP GET. The query string with the partial name of the city must be passed in the "q" query parameter:

![example response in postman](https://github.com/BujakiAttila/CitySuggestionAPI/blob/master/doc/suggestions.gif?raw=true)


### Source of city names
The data is "GeoNames Gazetteer" dataset.

For details see [the related readme file](http://download.geonames.org/export/dump/readme.txt) and the [GeoNames website](https://www.geonames.org/).
