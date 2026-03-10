package com.luv2code.component;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class ApplicationExampleTest {

	private static int counter = 0;

	@Value("${info.school.name}")
	private String schoolName;

	@Value("${info.app.name}")
	private String appName;

	@Value("${info.app.description}")
	private String appDescription;

	@Value("${info.app.version}")
	private String appVersion;

	@Autowired
	private CollegeStudent collegeStudents;

	@Autowired
	private StudentGrades studentsGrades;

	@BeforeEach
	public void beforeEachTest() {
		counter++;
		System.out.println("Testing : " + appName + " which is " + appDescription + " version :" + appVersion);

		collegeStudents.setFirstname("Bhaskar");
		collegeStudents.setLastname("Mudaliyar");
		collegeStudents.setEmailAddress("mudaliyarbhaskar29@gmail.com");

		studentsGrades.setMathGradeResults(new ArrayList<>(List.of(98.0, 76.0, 55.75, 87.0)));
		collegeStudents.setStudentGrades(studentsGrades);

	}

	@DisplayName("Add student Grade Result Test equals")
	@Test
	public void addStudentGradeResultTestEquals() {

		assertEquals(316.75,
				studentsGrades.addGradeResultsForSingleClass(collegeStudents.getStudentGrades().getMathGradeResults()));
	}
	
	
	@DisplayName("Add student Grade Result Test not equals")
	@Test
	public void addStudentGradeResultTestNotEquals() {

		assertNotEquals(100,
				studentsGrades.addGradeResultsForSingleClass(collegeStudents.getStudentGrades().getMathGradeResults()));
	}
}
