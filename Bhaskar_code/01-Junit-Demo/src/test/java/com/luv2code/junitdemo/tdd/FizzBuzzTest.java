package com.luv2code.junitdemo.tdd;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FizzBuzzTest {

	// if number divisible by 3 return Fizz
	// if number divisible by 5 return Buzz
	// if number divisible by 3 and 5 return FizzBuzz
	// if number is not divisible by 3 or 5 return number

	@DisplayName("Divisible by Three")
	@Order(1)
	@Test
	public void divisibleByThree() {

		String expected = "Fizz";
		assertEquals(expected, FizzBuzz.compute(3), "Divisible by 3");
	}

	@DisplayName("Divisible by Five")
	@Order(2)
	@Test
	public void divisibleByFive() {

		String expected = "Buzz";
		assertEquals(expected, FizzBuzz.compute(5), "Divisible by 5");
	}
}
