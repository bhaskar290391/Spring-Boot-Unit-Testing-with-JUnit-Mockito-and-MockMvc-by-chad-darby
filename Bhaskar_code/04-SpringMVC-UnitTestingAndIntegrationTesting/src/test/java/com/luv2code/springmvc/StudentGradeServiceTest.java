package com.luv2code.springmvc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	@Test
	public void deleteStudents() {
		Optional<CollegeStudent> deletedStudent = repo.findById(1);

		assertTrue(deletedStudent.isPresent());

		service.deleteStudent(1);
		
		deletedStudent = repo.findById(1);

		assertFalse(deletedStudent.isPresent());
	}
	
	@Test
	public void checkNumberOfStudents() {
		
		Iterable<CollegeStudent> gradeBook = service.getGradeBook();
		
		List<CollegeStudent> data=new ArrayList<>();
		
		
		for (CollegeStudent collegeStudent : gradeBook) {
			data.add(collegeStudent);
			
		}
		
		assertEquals(1, data.size());
	}

	@AfterEach
	public void cleanUpDatabase() {
		template.execute("delete from student ");
		template.execute("alter table student alter column id restart with 1");
	}
}
