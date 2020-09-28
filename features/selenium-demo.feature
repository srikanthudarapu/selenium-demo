Feature: Selenium Demo

  Scenario: Page displays Selenium Demo as title
    Given User opens Selenium Demo
    When User is on the Home page
    Then Selenium Demo is displayed as title

  Scenario: Basic features demo section is displayed
    Given User opens Selenium Demo
    And User is on the Home page
    When User clicks on Basic features demo button
    Then Basic features demo section is displayed