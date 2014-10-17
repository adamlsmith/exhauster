package com.pg.exhauster;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.Before;
import cucumber.api.java.en.*;
import cucumber.api.*;
import junit.framework.*;
import java.io.*;
import java.util.*;

public class ExhausterStepDefinition {
	App a = null;
	String s = null;

	@Given("^I am a system under low load$")
	public void i_am_a_system_under_low_load() throws Throwable {
		a = new App();
	}

	@When("^I request current memory usage$")
	public void i_request_current_memory_usage() throws Throwable {
		s = a.getMemoryUsage(0.99f);
	}

	@Then("^the heap usage should be less than \"(.*?)\"$")
	public void the_heap_usage_should_be_less_than(String arg1) throws Throwable {
		Integer i = Integer.parseInt(s.substring(12, s.indexOf("mb")));
		Assert.assertTrue(i < Integer.parseInt(arg1));

	}

	@Then("^the free memory should be greater than \"(.*?)\"$")
	public void the_free_memory_should_be_greater_than(String arg1) throws Throwable {
		Integer i = Integer.parseInt(s.substring(s.indexOf("ory:")+5, s.indexOf("mb", s.indexOf("mb")+1)));
		Assert.assertTrue(i > Integer.parseInt(arg1));
	}

}


