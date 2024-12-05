# celonis

0) Execution is fairly straight-forward.
1) Download the code from this reposity
2) run "mvn clean install"
3) execute the program

To use my implemented APIs, you need an API platform. I used POSTMAN for testing

To get an overview over the APIs, visit "http://localhost:8080/v3/api-docs" or "http://localhost:8080/swagger-ui/index.html". To download the yaml file, visit "http://localhost:8080/v3/api-docs.yaml"

Explanation:
1) Persist for location: POST http://localhost:8080/weather/<location>
2) Retrieve for given location: GET http://localhost:8080/weather/<location>
3) Retrieve all locations: GET http://localhost:8080/weather
