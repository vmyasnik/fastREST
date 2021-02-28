Feature: test

  Scenario: test GET
    * define
      | variable | value |
      | a        | 1     |
#      | method   | weather |
    * script
    """
    method = 'weather'
    """
    Given make GET request "https://community-open-weather-map.p.rapidapi.com/${method}"
    And send
    Then status code "401"
    Then print "a"
    And echo "a is ${a}"
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
    Then status code "200"

    When make GET request "https://petstore.swagger.io/v2/pet/1"
    And add headers
      | Content-Type | ${Content-Type} |
    And send
    Then status code "200"

    When make POST request "https://petstore.swagger.io/v2/pet/1"
      | x-www-urlencoded | true    |
      | name             | 'Dog'  |
      | status           | 'test1' |
    And add headers
      | Content-Type | application/x-www-form-urlencoded |
    And send
    Then status code "200"

    When make GET request "https://petstore.swagger.io/v2/pet/1"
    And add headers
      | Content-Type | ${Content-Type} |
    And send
    Then status code "200"


  Scenario: test PUT with json
    * define
      | variable     | value            |
      | id           | 1                |
      | Content-Type | application/json |
      | json         | test.json        |
    Given make PUT request "https://petstore.swagger.io/v2/pet"
      | body | ${json} |
    And add headers
      | Content-Type | ${Content-Type} |
    And send
    Then status code "200"

    When make DELETE request "https://petstore.swagger.io/v2/pet/1"
    And send
    Then status code "200"


    When make GET request "https://petstore.swagger.io/v2/pet/1"
    And add headers
      | Content-Type | ${Content-Type} |
    And send
    Then status code "404"