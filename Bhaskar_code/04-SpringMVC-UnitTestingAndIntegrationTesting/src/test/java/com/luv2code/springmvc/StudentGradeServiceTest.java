package com.luv2code.springmvc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import com.luv2code.springmvc.dao.HistoryGradeDao;
import com.luv2code.springmvc.dao.MathGradeDao;
import com.luv2code.springmvc.dao.ScienceGradeDao;
import com.luv2code.springmvc.dao.StudentDao;
import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.GradebookCollegeStudent;
import com.luv2code.springmvc.models.HistoryGrade;
import com.luv2code.springmvc.models.MathGrade;
import com.luv2code.springmvc.models.ScienceGrade;
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

	@Autowired
	private MathGradeDao mathGradeDao;

	@Autowired
	private ScienceGradeDao scienceGradeDao;

	@Autowired
	private HistoryGradeDao historyGradeDao;

	@Value("${sql.scripts.create.student}")
	private String createStudent;

	@Value("${sql.scripts.create.math.grade}")
	private String createMathGrade;
	@Value("${sql.scripts.create.science.grade}")
	private String createScienceGrade;
	@Value("${sql.scripts.create.history.grade}")
	private String createHistoryGrade;

	@Value("${sql.scripts.delete.student}")
	private String deleteStudent;

	@Value("${sql.scripts.delete.math.grade}")
	private String deleteMathGrade;
	@Value("${sql.scripts.delete.science.grade}")
	private String deleteScienceGrade;
	@Value("${sql.scripts.delete.history.grade}")
	private String deleteHistoryGrade;

	@BeforeEach
	public void setupDatabase() {
		template.execute(createStudent);

		template.execute(createMathGrade);
		template.execute(createScienceGrade);
		template.execute(createHistoryGrade);
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
		Optional<MathGrade> gradeByStudentId = mathGradeDao.findById(1);
		Optional<ScienceGrade> scienceGradeByStudentId = scienceGradeDao.findById(1);
		Optional<HistoryGrade> historyGradeByStudentId = historyGradeDao.findById(1);

		assertTrue(deletedStudent.isPresent());
		assertTrue(gradeByStudentId.isPresent());
		assertTrue(scienceGradeByStudentId.isPresent());
		assertTrue(historyGradeByStudentId.isPresent());

		service.deleteStudent(1);

		deletedStudent = repo.findById(1);
		gradeByStudentId = mathGradeDao.findById(1);
		scienceGradeByStudentId = scienceGradeDao.findById(1);
		historyGradeByStudentId = historyGradeDao.findById(1);

		assertFalse(deletedStudent.isPresent());
		assertFalse(gradeByStudentId.isPresent());
		assertFalse(scienceGradeByStudentId.isPresent());
		assertFalse(historyGradeByStudentId.isPresent());
	}

	@Sql("/insert-data.sql")
	@Test
	public void checkNumberOfStudents() {

		Iterable<CollegeStudent> gradeBook = service.getGradeBook();

		List<CollegeStudent> data = new ArrayList<>();

		for (CollegeStudent collegeStudent : gradeBook) {
			data.add(collegeStudent);

		}

		assertEquals(5, data.size());
	}

	@Test
	public void createGrade() {

		assertTrue(service.createGrade(85.0, 1, "math"));
		assertTrue(service.createGrade(85.0, 1, "science"));
		assertTrue(service.createGrade(85.0, 1, "history"));

		Iterable<MathGrade> mathData = mathGradeDao.findGradeByStudentId(1);
		Iterable<ScienceGrade> scienceData = scienceGradeDao.findGradeByStudentId(1);
		Iterable<HistoryGrade> historyData = historyGradeDao.findGradeByStudentId(1);

		assertTrue(mathData.iterator().hasNext());
		assertTrue(scienceData.iterator().hasNext());
		assertTrue(historyData.iterator().hasNext());

		assertTrue(((Collection<MathGrade>) mathData).size() == 2);
		assertTrue(((Collection<ScienceGrade>) scienceData).size() == 2);
		assertTrue(((Collection<HistoryGrade>) historyData).size() == 2);
	}

	@Test
	public void createGradeFailingTest() {

		assertFalse(service.createGrade(105, 1, "math"));
		assertFalse(service.createGrade(-5, 1, "math"));
		assertFalse(service.createGrade(85.0, 2, "history"));
		assertFalse(service.createGrade(85.0, 1, "literature"));

	}

	@Test
	public void deleteGradeService() {

		assertEquals(1, service.deleteGrade(1, "math"));
		assertEquals(1, service.deleteGrade(1, "science"));
		assertEquals(1, service.deleteGrade(1, "history"));
	}

	@Test
	public void deleteGradeServiceForInvalidGradeIdAndGradeType() {

		assertEquals(0, service.deleteGrade(0, "math"));
		assertEquals(0, service.deleteGrade(1, "Literature"));

	}

	@Test
	public void studentInformation() {
		GradebookCollegeStudent student = service.studentInformation(1);
		assertNotNull(student);
		assertEquals(1, student.getId());
		assertEquals("bhaskar", student.getFirstname());
		assertEquals("mudaliyar", student.getLastname());
		assertEquals("bhaskar@gmail.com", student.getEmailAddress());
		assertTrue(student.getStudentGrades().getHistoryGradeResults().size() == 1);
		assertTrue(student.getStudentGrades().getMathGradeResults().size() == 1);
		assertTrue(student.getStudentGrades().getScienceGradeResults().size() == 1);
	}

	@Test
	public void studentInformationForInvalidStudent() {
		GradebookCollegeStudent student = service.studentInformation(0);
		assertNull(student);

	}

	@AfterEach
	public void cleanUpDatabase() {
		template.execute(deleteStudent);
		template.execute(deleteHistoryGrade);
		template.execute(deleteMathGrade);
		template.execute(deleteScienceGrade);

		
	}
}
