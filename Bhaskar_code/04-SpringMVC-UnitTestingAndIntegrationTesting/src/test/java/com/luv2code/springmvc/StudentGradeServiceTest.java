package com.luv2code.springmvc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.luv2code.springmvc.dao.StudentDao;
import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.service.StudentAndGradeService;

@SpringBootTest
@TestPropertySource("/application.properties")
public class StudentGradeServiceTest {

	@Autowired
	private StudentAndGradeService service;

	@Autowired
	private StudentDao repo;

	@Test
	public void createStudent() {
		service.createStudent("Bhaskar", "Mudaliyar", "bhaksar@gmail.com");

		CollegeStudent student = repo.findByEmailAddress("bhaksar@gmail.com");

		assertEquals("bhaksar@gmail.com", student.getEmailAddress());

	}
}
