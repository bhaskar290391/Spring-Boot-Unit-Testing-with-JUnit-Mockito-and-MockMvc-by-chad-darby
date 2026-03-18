package com.luv2code.junitdemo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;


//@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
//@DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class DemoUtilTest {

	private DemoUtils utils;

	@BeforeEach
	public void beforeEachTest() {
		utils = new DemoUtils();
		System.out.println("Executing before each Test ");
	}

	@AfterEach
	public void afterEachTest() {

		System.out.println("Executing After each Test ");
		System.out.println();
	}

	@BeforeAll
	public static void beforeAllData() {
		System.out.println("Before all test");
		System.out.println();
	}

	@AfterAll
	public static void afterAllData() {
		System.out.println("After all test");
	}

	@Test
	@DisplayName("Equals and Not Equals")
	public void checkEqualsAndNotEquals() {
		System.out.println("Test : checkEqualsAndNotEquals");
		assertEquals(6, utils.add(2, 4), "is valid");
		assertNotEquals(6, utils.add(1, 9), "Invalid addition");

	}

	@Test
	@DisplayName("Null and Not Null")
	public void check_Null_And_NotNulls() {
		System.out.println("Test : checkNullAndNotNotNulls");
		String str1 = null;
		String str2 = "bhaskar";
		assertNull(utils.checkNull(str1));
		assertNotNull(utils.checkNull(str2));
	}
	
	@Test
	@DisplayName("Same and Not Same")
	public void sameAndNotSame() {
		
		String str="bhaskar";
		assertSame(utils.getAcademy(), utils.getAcademyDuplicate());
		assertNotSame(utils.getAcademy(), str);
	}
	
	
	@Test
	@DisplayName("True and False")
	public void trueAndFalse() {
		assertTrue(utils.isGreater(10, 5));
		assertFalse(utils.isGreater(5, 15));
	}
}
