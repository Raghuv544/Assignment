package com.Automation.Runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.*;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/FeatureFiles", 
     glue = {"com.Automation.Steps"},
     plugin = {"pretty","html:target/cucumber.html"},// to generate different type of report
     monochrome = true,//display output in the readable format
     dryRun = false, // Generates the undefined step definitions only , not executes the other steps
     publish = true,
   tags = "@tag1new")
public class CucumberTestRunner {

}
