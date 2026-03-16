package com.luv2code.springmvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

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

	@Mock
	private StudentAndGradeService serviceMock;

	@BeforeEach
	public void setupDatabase() {
		template.execute(
				"insert into student(firstname,lastname,email_address) values ('bhaskar','mudaliyar','kanishk@gmail,com')");
	}

	@Test
	public void getStudentsHttpRequest() throws Exception {

		CollegeStudent one = new GradebookCollegeStudent("Kanishk", "Mudaliyar", "kanishk@gmail.com");
		CollegeStudent two = new GradebookCollegeStudent("sammy", "Mudaliyar", "sammy@gmail.com");

		List<CollegeStudent> data = new ArrayList<>(Arrays.asList(one, two));
		when(serviceMock.getGradeBook()).thenReturn(data);
		assertIterableEquals(data, serviceMock.getGradeBook());

		MvcResult mavData = mockMVC.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk()).andReturn();
		ModelAndView datas = mavData.getModelAndView();

		ModelAndViewAssert.assertViewName(datas, "index");

	}

	@AfterEach
	public void cleanUpDatabase() {
		template.execute("delete from student ");
		template.execute("alter table student alter column id restart with 1");
	}
}
