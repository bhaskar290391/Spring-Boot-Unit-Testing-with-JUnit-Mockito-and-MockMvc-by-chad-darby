package com.luv2code.junitdemo.tdd;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FizzBuzzTest {

	// if number divisible by 3 return Fizz
	@DisplayName("Divisible by Three")
	@Order(1)
	@Test
	public void divisibleByThree() {

		String expected = "Fizz";
		assertEquals(expected, FizzBuzz.compute(3), "Divisible by 3");
	}

	// if number divisible by 5 return Buzz
	@DisplayName("Divisible by Five")
	@Order(2)
	@Test
	public void divisibleByFive() {

		String expected = "Buzz";
		assertEquals(expected, FizzBuzz.compute(5), "Divisible by 5");
	}

	// if number divisible by 3 and 5 return FizzBuzz
	@DisplayName("Divisible by Three and Five")
	@Order(3)
	@Test
	public void divisibleByThreeAndFive() {

		String expected = "FizzBuzz";
		assertEquals(expected, FizzBuzz.compute(15), "Divisible by 3 and 5");
	}

	// if number is not divisible by 3 or 5 return number
	@DisplayName("Divisible by Three Or Five")
	@Order(4)
	@Test
	public void divisibleByThreeOrFive() {

		String expected = "1";
		assertEquals(expected, FizzBuzz.compute(1), "Not Divisible by 3 or 5");
	}

	// if number is not divisible by 3 or 5 return number
	@DisplayName("Parametized test using csv file")
	@Order(5)
	@ParameterizedTest(name = "value={0},expected={1}")
	@CsvFileSource(resources = "/small-data-file.csv")
	public void paremterizedTest(int value, String expected) {

		assertEquals(expected, FizzBuzz.compute(value), "Parametized Tets");
	}
}
