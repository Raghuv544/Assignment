#Author: your.email@your.domain.com
@Letstalktea
Feature: LetsTalkTea Application scenario's

  @Letstalktea_Tc1
  Scenario: Title of your scenario
    Then Verify CheckOut link navigation
    When Enter CheckOut information
    Then Submit the CheckOut

  @Letstalktea_Tc2
  Scenario: Checkout_Page_information
    Then Verify CheckOut link navigation
    When Enter CheckOut Customer information
    And Enter Checkout Payment information
    Then Submit the CheckOut

  @Letstalktea_Tc3
  Scenario: Checkout_Page_information and Payments
    Then Verify CheckOut link navigation
    When Enter "abcdef@gmail.com","xyz","chennai" CheckOut Customer information
    And Enter Checkout Payment information
    Then Submit the CheckOut

  @Letstalktea_Tc4
  Scenario Outline: Checkout_Page_information and Payments
    Then Verify CheckOut link navigation
    When Enter "<Email>","<Name>","<Address>" CheckOutPage Customer information
    And Enter Checkout Payment information
    Then Submit the CheckOut

    Examples: 
      | Email             | Name     | Address   |
      | lettalk@gmail.com | sabarish | Bangalore |
      | Teatalk@gmail.com | Magesh   | chennai   |
 