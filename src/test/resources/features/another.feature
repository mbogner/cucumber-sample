Feature: the other feature can be retrieved
  Scenario: client makes call to GET /another
    When the client calls /another
    Then the client also receives status code of 200
    And the client receives the response 2