package com.luv2code.springmvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.MathGrade;
import com.luv2code.springmvc.repository.HistoryGradesDao;
import com.luv2code.springmvc.repository.MathGradesDao;
import com.luv2code.springmvc.repository.ScienceGradesDao;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class GradeBookControllerTest {

	private static MockHttpServletRequest request;

	@Mock
	private StudentAndGradeService mockService;

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private MockMvc mockmvc;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	CollegeStudent collegeStudents;

	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private MathGradesDao mathGradeDao;

	@Autowired
	private ScienceGradesDao scienceGradeDao;

	@Autowired
	private HistoryGradesDao historyGradeDao;

	@Autowired
	private StudentAndGradeService studentService;

	@Value("${sql.script.create.student}")
	private String sqlAddStudent;

	@Value("${sql.script.create.math.grade}")
	private String sqlAddMathGrade;

	@Value("${sql.script.create.science.grade}")
	private String sqlAddScienceGrade;

	@Value("${sql.script.create.history.grade}")
	private String sqlAddHistoryGrade;

	@Value("${sql.script.delete.student}")
	private String sqlDeleteStudent;

	@Value("${sql.script.delete.math.grade}")
	private String sqlDeleteMathGrade;

	@Value("${sql.script.delete.science.grade}")
	private String sqlDeleteScienceGrade;

	@Value("${sql.script.delete.history.grade}")
	private String sqlDeleteHistoryGrade;

	public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

	@BeforeAll
	public static void setup() {
		request = new MockHttpServletRequest();
		request.setParameter("firstName", "Bhaskar");
		request.setParameter("lastName", "Mudaliyar");
		request.setParameter("email", "mudaliyarbhaskar29@gmail.com");
	}

	@BeforeEach
	public void setupDatabase() {
		jdbc.execute(sqlAddStudent);
		jdbc.execute(sqlAddMathGrade);
		jdbc.execute(sqlAddScienceGrade);
		jdbc.execute(sqlAddHistoryGrade);
	}

	@Test
	public void getStudentsHttpRequest() throws Exception {

		collegeStudents.setFirstname("Bhaskar");
		collegeStudents.setLastname("shetty");
		collegeStudents.setEmailAddress("hello@gmail.com");
		manager.persist(collegeStudents);
		manager.flush();

		mockmvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(2)));

	}

	@Test
	public void createStudents() throws Exception {

		collegeStudents.setFirstname("Bhaskar");
		collegeStudents.setLastname("maddy");
		collegeStudents.setEmailAddress("maddy@gmail.com");

		mockmvc.perform(
				post("/").contentType(APPLICATION_JSON_UTF8).content(objectMapper.writeValueAsString(collegeStudents)))
				.andExpect(jsonPath("$", hasSize(2)));

		assertNotNull(studentDao.findByEmailAddress("maddy@gmail.com"));
	}

	@Test
	public void deleteStudents() throws Exception {

		assertTrue(studentDao.findById(1).isPresent());

		mockmvc.perform(MockMvcRequestBuilders.delete("/student/{id}", 1)).andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(0)));

		assertFalse(studentDao.findById(1).isPresent());
	}

	@Test
	public void deleteStudentsWhoDoesNotExists() throws Exception {

		assertFalse(studentDao.findById(0).isPresent());

		mockmvc.perform(MockMvcRequestBuilders.delete("/student/{id}", 0)).andExpect(status().is4xxClientError())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.message", is("Student or Grade was not found")))
				.andExpect(jsonPath("$.status", is(404)));

	}

	@Test
	public void getStudentsInformationHttpRequest() throws Exception {

		Optional<CollegeStudent> student = studentDao.findById(1);

		assertTrue(student.isPresent());

		mockmvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}", 1)).andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.firstname", is("Eric"))).andExpect(jsonPath("$.lastname", is("Roby")));

	}

	@Test
	public void getStudentsInformationHttpRequestWhoDoesNotExists() throws Exception {

		assertFalse(studentDao.findById(0).isPresent());

		mockmvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}", 0))
				.andExpect(status().is4xxClientError()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.message", is("Student or Grade was not found")))
				.andExpect(jsonPath("$.status", is(404)));

	}

	@Test
	public void createValidGradeForStudent() throws Exception {

		mockmvc.perform(post("/grades").contentType(APPLICATION_JSON_UTF8).param("grade", "85.0")
				.param("gradeType", "math").param("studentId", "1"))
				.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.firstname", is("Eric"))).andExpect(jsonPath("$.lastname", is("Roby")));

	}

	@Test
	public void createValidGradeForStudentWhoDoesNotExist() throws Exception {

		mockmvc.perform(post("/grades").contentType(APPLICATION_JSON_UTF8).param("grade", "85.0")
				.param("gradeType", "math").param("studentId", "0")).andExpect(status().is4xxClientError())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.message", is("Student or Grade was not found")))
				.andExpect(jsonPath("$.status", is(404)));

	}

	@Test
	public void createValidGradeForStudentInvalidGradeType() throws Exception {

		mockmvc.perform(post("/grades").contentType(APPLICATION_JSON_UTF8).param("grade", "85.0")
				.param("gradeType", "Literature").param("studentId", "1")).andExpect(status().is4xxClientError())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.message", is("Student or Grade was not found")))
				.andExpect(jsonPath("$.status", is(404)));

	}

	@Test
	public void deleteGrades() throws Exception {

		Optional<MathGrade> byId = mathGradeDao.findById(1);

		assertTrue(byId.isPresent());

		mockmvc.perform(MockMvcRequestBuilders.delete("/grades/{id}/{gradeType}", 1, "math")).andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.firstname", is("Eric"))).andExpect(jsonPath("$.lastname", is("Roby")));

	}

	@Test
	public void deleteGradesInvalidGradeType() throws Exception {

		mockmvc.perform(MockMvcRequestBuilders.delete("/grades/{id}/{gradeType}", 1, "Literature"))
				.andExpect(status().is4xxClientError()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.message", is("Student or Grade was not found")))
				.andExpect(jsonPath("$.status", is(404)));

	}

	@Test
	public void deleteGradesInvalidGradeTypeAndGrade() throws Exception {

		mockmvc.perform(MockMvcRequestBuilders.delete("/grades/{id}/{gradeType}", 0, "Literature"))
				.andExpect(status().is4xxClientError()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.message", is("Student or Grade was not found")))
				.andExpect(jsonPath("$.status", is(404)));

	}

	@AfterEach
	public void setupAfterTransaction() {
		jdbc.execute(sqlDeleteStudent);
		jdbc.execute(sqlDeleteMathGrade);
		jdbc.execute(sqlDeleteScienceGrade);
		jdbc.execute(sqlDeleteHistoryGrade);
	}
}
