package com.UAT.SAMUI.core.generic.utilities;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(strict = true, monochrome = true, 
                 features = "src/test/resources/Feature", 
                 glue = "com.UAT.SAMUI.automation.steps", 
                 plugin = {	"pretty", "html:target/cucumber-html-report", 
                		 "json:target/cucumber-reports/Cucumber.json",
                		 "com.cucumber.listener.ExtentCucumberFormatter:test-output/cucumber-html-report/report.html" }, 
                 tags = { "@Enrollment1" })

public class CucumberRunner extends AbstractTestNGCucumberTests {

}
