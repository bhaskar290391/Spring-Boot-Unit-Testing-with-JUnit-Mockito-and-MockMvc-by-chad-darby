package com.luv2code.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.dao.ApplicationDao;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import com.luv2code.component.service.ApplicationService;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class MockitoExampleTest {

//	@Mock
//	private ApplicationDao repo;
//
//	@InjectMocks
//	private ApplicationService service;
	

	@MockitoBean
	private ApplicationDao repo;

	@Autowired
	private ApplicationService service;

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
	}

	@DisplayName("When and Verify")
	@Test
	public void checkWhenAndVerify() {

		when(repo.addGradeResultsForSingleClass(students.getStudentGrades().getMathGradeResults())).thenReturn(100.0);

		assertEquals(100.0, service.addGradeResultsForSingleClass(students.getStudentGrades().getMathGradeResults()));

		verify(repo, times(1)).addGradeResultsForSingleClass(students.getStudentGrades().getMathGradeResults());

	}
	
	
	@DisplayName("Find GPA")
	@Test
	public void findGPA() {
		when(repo.findGradePointAverage(grades.getMathGradeResults())).thenReturn(85.32);
		
		assertEquals(85.32, service.findGradePointAverage(students.getStudentGrades().getMathGradeResults()));
	}
	
	
	@DisplayName("Check Not Null")
	@Test
	public void checkNotNull() {
		
		when(repo.checkNull(grades.getMathGradeResults())).thenReturn(true);
		
		assertNotNull(service.checkNull(students.getStudentGrades().getMathGradeResults()));
	}
}
