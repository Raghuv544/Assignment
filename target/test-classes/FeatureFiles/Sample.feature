#Author:Raghu

@tag
Feature: Google Application Scenario's

  @GoogleTc1
  Scenario: Verify Google Search
    Given Open Google in Chrome Browser
    When Enter any Data in SearchBox
    Then Close the Google browser
    
   @GoogleTc2
  Scenario: Verify Google Search
    Given Open Google in Chrome Browser
    When Enter "Manual" keyword in SearchBox
    Then Close the Google browser
    

