package com.pg.exhauster;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
	format = {"html:target/cucumber-html-report", "json:target/cucumber-json-report.json"},
	glue = {"com.pg.exhauster"}
		
		)
public class RunCukesTest{
}
