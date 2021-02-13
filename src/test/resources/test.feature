Feature: test

  Scenario: test GET
    * define
      | variable | value   |
      | a        | 1       |
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

