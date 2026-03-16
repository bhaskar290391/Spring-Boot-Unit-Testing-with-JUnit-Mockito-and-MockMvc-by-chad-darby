package com.luv2code.springmvc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
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

	@Autowired
	private JdbcTemplate template;

	@BeforeEach
	public void setupDatabase() {
		template.execute(
				"insert into student(firstname,lastname,email_address) values ('bhaskar','mudaliyar','kanishk@gmail,com')");
	}

	@Test
	public void createStudent() {
		service.createStudent("Bhaskar", "Mudaliyar", "bhaksar@gmail.com");

		CollegeStudent student = repo.findByEmailAddress("bhaksar@gmail.com");

		assertEquals("bhaksar@gmail.com", student.getEmailAddress());

	}
	
	
	@Test
	public void checkStudentExist() {
		assertTrue(service.checkStudentIsNull(1));
		assertFalse(service.checkStudentIsNull(0));
	}

	@AfterEach
	public void cleanUpDatabase() {
		template.execute("delete from student ");
		template.execute("alter table student alter column id restart with 1");
	}
}
