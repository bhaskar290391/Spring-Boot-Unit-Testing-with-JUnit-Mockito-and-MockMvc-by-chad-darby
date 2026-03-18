package com.luv2code.junitdemo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

//@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
//@DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)

//@TestMethodOrder(MethodOrderer.DisplayName.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
	@Order(1)
	@DisplayName("Equals and Not Equals")
	public void checkEqualsAndNotEquals() {
		System.out.println("Test : checkEqualsAndNotEquals");
		assertEquals(6, utils.add(2, 4), "is valid");
		assertNotEquals(6, utils.add(1, 9), "Invalid addition");

	}

	@Test
	@Order(2)
	@DisplayName("Null and Not Null")
	public void check_Null_And_NotNulls() {
		System.out.println("Test : checkNullAndNotNotNulls");
		String str1 = null;
		String str2 = "bhaskar";
		assertNull(utils.checkNull(str1));
		assertNotNull(utils.checkNull(str2));
	}

	@Test
	@Order(3)
	@DisplayName("Same and Not Same")
	public void sameAndNotSame() {

		String str = "bhaskar";
		assertSame(utils.getAcademy(), utils.getAcademyDuplicate());
		assertNotSame(utils.getAcademy(), str);
	}

	@Test
	@Order(4)
	@DisplayName("True and False")
	public void trueAndFalse() {
		assertTrue(utils.isGreater(10, 5));
		assertFalse(utils.isGreater(5, 15));
	}

	@Test
	@Order(5)
	@DisplayName("Arrays Equals")
	public void arraysEquals() {
		String[] str = { "A", "B", "C" };
		assertArrayEquals(utils.getFirstThreeLettersOfAlphabet(), str);
	}

	@Test
	@Order(5)
	@DisplayName("Iterable Equals")
	public void iterableEquals() {
		List<String> data = List.of("luv", "2", "code");
		assertIterableEquals(data, utils.getAcademyInList());
	}

	@Test
	@Order(6)
	@DisplayName("Throws and Does Not Throws")
	public void throwsDoesNotThrows() {
		assertThrows(Exception.class, () -> {
			utils.throwException(-1);
		});
		assertDoesNotThrow(() -> {
			utils.throwException(1);
		});
	}

	@Test
	@Order(7)
	@DisplayName("Timeout")
	public void checkout() {
		assertTimeoutPreemptively(Duration.ofSeconds(3), () -> {
			utils.checkTimeout();
		});
	}

}
