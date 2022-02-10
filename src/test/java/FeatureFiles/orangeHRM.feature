@OrangeHRM
Feature: OrangeHRM

  @OrangeHRM_systemUsers
  Scenario: check systemUsers navigation
    Given verify orangeHRM login
    Then navigate to systemUsers

  @OrangeHRM_myInfo
  Scenario: check My Info link
    Given verify orangeHRM login
    Then navigate to My Info link

  @OrangeHRM_Trackers
  Scenario: check Trackers link
    Given verify orangeHRM login
    Then navigate to Trackers link

    @OrangeHRM_EmployeeTimeSheet
  Scenario: check EmployeeTimeSheet link
    Given verify orangeHRM login
    Then navigate to  EmployeeTimeSheet link 
   
    @OrangeHRM_ReportingMethodsLink
  Scenario: check ReportingMethods link
    Given verify orangeHRM login
    Then navigate to ReportingMethodsLink
    
    @OrangeHRM_Adduser
  Scenario: check AddUser Details
    Given verify orangeHRM login
    Then navigate to systemUsers
    And click on add button and navigate to add user page
    Then enter the details and click on save button
    
    
    
   