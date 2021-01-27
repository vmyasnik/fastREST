Feature: test

  Scenario: test GET
    * define
      | variable | value |
      | a        | 1     |
    Given make GET request "https://community-open-weather-map.p.rapidapi.com/weather"
    And send
    * print "a"