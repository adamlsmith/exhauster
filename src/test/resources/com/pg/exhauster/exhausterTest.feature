Feature: Memory Test
Scenario: Check memory usage
	Given I am a system under low load
	When I request current memory usage
	Then the heap usage should be less than "1000"
	And the free memory should be greater than "1"

