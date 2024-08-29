Feature: Inventory

  As a logged in user of the Sauce Labs website
  I want to be able to view available products and add them to a shopping cart

  Background:
    Given I am on the home page
    And I have entered the username "standard_user"
    And I have entered the password "secret_sauce"
    And I click the login button



  Scenario: View available products
    When I land on the inventory page
    And The number of available products is 6

  Scenario: Adding an item to the cart increments the count
    When I land on the inventory page
    And I add an item to the cart
    Then The item count increases by one