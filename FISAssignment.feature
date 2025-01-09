
@manash
Feature: Assignment for FIS
  

 @test
  Scenario: Ebay add to cart
  Given I open the ebay site
      When I search for a book
      And I select he first book
      And i add to cart
      Then I verify the cart
  
    