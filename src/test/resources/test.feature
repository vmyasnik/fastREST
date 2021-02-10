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