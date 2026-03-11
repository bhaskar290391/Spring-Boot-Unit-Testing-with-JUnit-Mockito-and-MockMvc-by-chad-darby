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
import org.springframework.context.ApplicationContext;

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

	@Autowired
	private ApplicationContext context;

	@BeforeEach
	public void beforeEachTest() {
		counter++;
		System.out.println("Testing : " + appName + " which is " + appDescription + " version :" + appVersion
				+ " executed for " + counter);

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

	@DisplayName("check for student grade null")
	@Test
	public void checkNullStudentGrades() {
		assertNotNull(studentsGrades.checkNull(collegeStudents.getStudentGrades().getMathGradeResults()));
	}

	@DisplayName("check is greater than true")
	@Test
	public void checkIsGreaterTrue() {
		assertTrue(studentsGrades.isGradeGreater(90, 80));
	}

	@DisplayName("check is greater than false")
	@Test
	public void checkIsGreaterfalse() {
		assertFalse(studentsGrades.isGradeGreater(80, 90));
	}

	@DisplayName("Check students are of type prototype")
	@Test
	public void checkPrototype() {
		CollegeStudent student2 = context.getBean(CollegeStudent.class);
		assertNotSame(student2, collegeStudents);
	}

	@DisplayName("Students without grade init")
	@Test
	public void withoutGrade() {
		CollegeStudent student2 = context.getBean(CollegeStudent.class);
		student2.setFirstname("Bhaskar");
		student2.setLastname("Mudaliyar");
		student2.setEmailAddress("mudaliyarbhaskar29@gmail.com");
		assertNotNull(student2.getFirstname());
		assertNotNull(student2.getLastname());
		assertNotNull(student2.getEmailAddress());

		assertNull(studentsGrades.checkNull(student2.getStudentGrades()));

	}

	@DisplayName("Find Grade point average")
	@Test
	public void gradePointAverage() {
		assertAll("Testing all assert together",
				() -> assertEquals(316.75,
						studentsGrades.addGradeResultsForSingleClass(
								collegeStudents.getStudentGrades().getMathGradeResults())),
				() -> assertEquals(79.19, studentsGrades
						.findGradePointAverage(collegeStudents.getStudentGrades().getMathGradeResults())));
	}

}
