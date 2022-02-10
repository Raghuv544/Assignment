
@Heroku_App
Feature: Heroku Home and signup page
 
  @Homepage
  Scenario: Enter text in Searchbox 
    Given search the element in search box
 
 @SignUpPage
  Scenario: Enter the details in signUp page
    Given click the signup page link
    Then enter the details in the box
   