Feature: test

  Scenario: test GET
    * define
      | variable | value   |
      | a        | 1       |
      | method   | weather |
    Given make GET request "https://community-open-weather-map.p.rapidapi.com/${method}"
    And send
    * print "a"
    * echo "a is ${a}"

  Scenario: test POST with json
    * define
      | variable     | value            |
      | id           | 1                |
      | Content-Type | application/json |
      | json         | test.json        |
    Given make POST request "https://petstore.swagger.io/v2/pet"
      | body | ${json} |
    And add headers
      | Content-Type | ${Content-Type} |
    And send
