package com.luv2code.springmvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import com.luv2code.springmvc.dao.StudentDao;
import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.GradebookCollegeStudent;
import com.luv2code.springmvc.service.StudentAndGradeService;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application.properties")
public class GradebookControllerTest {
	@Autowired
	private JdbcTemplate template;

	@Autowired
	private MockMvc mockMVC;

	private static MockHttpServletRequest request;

	@Mock
	private StudentAndGradeService studentCreateServiceMock;

	@Autowired
	private StudentDao studentDao;
	
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

	@BeforeAll
	public static void requestSetup() {
		request = new MockHttpServletRequest();
		request.setParameter("firstname", "soni");
		request.setParameter("lastname", "mudaliyar");
		request.setParameter("emailAddress", "soni@gmail.com");
	}

	@BeforeEach
	public void setupDatabase() {
		template.execute(createStudent);

		template.execute(createMathGrade);
		template.execute(createScienceGrade);
		template.execute(createHistoryGrade);
	}

	@Test
	public void getStudentsHttpRequest() throws Exception {

		CollegeStudent one = new GradebookCollegeStudent("Kanishk", "Mudaliyar", "kanishk@gmail.com");
		CollegeStudent two = new GradebookCollegeStudent("sammy", "Mudaliyar", "sammy@gmail.com");

		List<CollegeStudent> data = new ArrayList<>(Arrays.asList(one, two));
		when(studentCreateServiceMock.getGradeBook()).thenReturn(data);
		assertIterableEquals(data, studentCreateServiceMock.getGradeBook());

		MvcResult mavData = mockMVC.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk()).andReturn();
		ModelAndView datas = mavData.getModelAndView();

		ModelAndViewAssert.assertViewName(datas, "index");

	}

	@Test
	public void createStudents() throws Exception {

		CollegeStudent one = new GradebookCollegeStudent("Kanishk", "Mudaliyar", "kanishk@gmail.com");

		List<CollegeStudent> data = new ArrayList<>(Arrays.asList(one));
		when(studentCreateServiceMock.getGradeBook()).thenReturn(data);
		assertIterableEquals(data, studentCreateServiceMock.getGradeBook());

		MvcResult mavData = mockMVC
				.perform(post("/").contentType(MediaType.APPLICATION_JSON)
						.param("firstname", request.getParameter("firstname"))
						.param("lastname", request.getParameter("lastname"))
						.param("emailAddress", request.getParameter("emailAddress")))
				.andExpect(status().isOk()).andReturn();

		ModelAndView datas = mavData.getModelAndView();

		ModelAndViewAssert.assertViewName(datas, "index");

		CollegeStudent byEmailAddress = studentDao.findByEmailAddress("soni@gmail.com");
		assertNotNull(byEmailAddress);
	}

	@Test
	public void deleteStudent() throws Exception {
		assertTrue(studentDao.findById(1).isPresent());
		MvcResult mavData = mockMVC.perform(MockMvcRequestBuilders.get("/student/delete/{id}", 1))
				.andExpect(status().isOk()).andReturn();
		ModelAndView datas = mavData.getModelAndView();

		ModelAndViewAssert.assertViewName(datas, "index");

		assertFalse(studentDao.findById(1).isPresent());

	}

	@Test
	public void deleteStudentForerrorPage() throws Exception {

		MvcResult mavData = mockMVC.perform(MockMvcRequestBuilders.get("/student/delete/{id}", 0))
				.andExpect(status().isOk()).andReturn();
		ModelAndView datas = mavData.getModelAndView();

		ModelAndViewAssert.assertViewName(datas, "error");

	}

	@AfterEach
	public void cleanUpDatabase() {
		template.execute(deleteStudent);
		template.execute(deleteHistoryGrade);
		template.execute(deleteMathGrade);
		template.execute(deleteScienceGrade);
	}
}
