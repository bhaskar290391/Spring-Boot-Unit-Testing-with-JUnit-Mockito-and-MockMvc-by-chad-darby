package com.luv2code.test;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class ReflectionTestUtilsData {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private CollegeStudent students;

	@Autowired
	private StudentGrades grades;

	@BeforeEach
	public void beforeEachData() {
		students.setFirstname("Bhaskar");
		students.setLastname("Mudaliyar");
		students.setEmailAddress("abc@test.com");
		students.setStudentGrades(grades);

		ReflectionTestUtils.setField(students, "id", 29);
		ReflectionTestUtils.setField(students, "studentGrades", new StudentGrades(Arrays.asList(55.0,45.0,66.9)));
	}
	
	
	@DisplayName("checking with private field")
	@Test
	public void checkingPrivateFiled() {
		assertEquals(29, ReflectionTestUtils.getField(students, "id"));
	}
	
	
	@DisplayName("Invoking private Method")
	@Test
	public void invokingPrivateMethod() {
		assertEquals("Bhaskar 29", ReflectionTestUtils.invokeMethod(students, "getFirsNameAndId", null));
	}

}
