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
    And check
      | $.id | == | ${id} |

    When make GET request "https://petstore.swagger.io/v2/pet/1"
    And add headers
      | Content-Type | ${Content-Type} |
    And send
    Then status code "200"

    When make POST request "https://petstore.swagger.io/v2/pet/1"
      | x-www-urlencoded | true    |
      | name             | 'Dog'   |
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

  Scenario: Execute curl
    * define
      | variable     | value            |
      | Content-Type | application/json |
    When execute command line:
      """
      curl -X POST "https://petstore.swagger.io/v2/pet" -H  "accept: ${Content-Type}" -H  "Content-Type: application/json" -d "{  \"id\": 1,  \"category\": {    \"id\": 1,    \"name\": \"1\"  },  \"name\": \"doggie\",  \"photoUrls\": [    \"string\"  ],  \"tags\": [    {      \"id\": 0,      \"name\": \"string\"    }  ],  \"status\": \"available\"}"
      """

    When execute command line:
      """
      ls -la
      """