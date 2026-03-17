package com.luv2code.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springmvc.models.*;
import com.luv2code.springmvc.service.StudentAndGradeService;

@Controller
public class GradebookController {

	private final HistoryGrade getHistoryGrade;

	private final ScienceGrade getScienceGrade;

	private final CollegeStudent getCollegeStudent;

	@Autowired
	private Gradebook gradebook;

	@Autowired
	private StudentAndGradeService service;

	GradebookController(CollegeStudent getCollegeStudent, ScienceGrade getScienceGrade, HistoryGrade getHistoryGrade) {
		this.getCollegeStudent = getCollegeStudent;
		this.getScienceGrade = getScienceGrade;
		this.getHistoryGrade = getHistoryGrade;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getStudents(Model m) {

		Iterable<CollegeStudent> gradeBook2 = service.getGradeBook();
		m.addAttribute("students", gradeBook2);
		return "index";
	}

	@GetMapping(value = "/student/delete/{id}")
	public String deleteStudents(@PathVariable("id") int id, Model m) {

		if (!service.checkStudentIsNull(id)) {
			return "error";
		}
		service.deleteStudent(id);
		Iterable<CollegeStudent> gradeBook2 = service.getGradeBook();
		m.addAttribute("students", gradeBook2);
		return "index";
	}

	@PostMapping
	public String createStudent(@ModelAttribute("student") CollegeStudent student, Model m) {

		service.createStudent(student.getFirstname(), student.getLastname(), student.getEmailAddress());
		Iterable<CollegeStudent> gradeBook2 = service.getGradeBook();
		m.addAttribute("students", gradeBook2);
		return "index";
	}

	@GetMapping("/studentInformation/{id}")
	public String studentInformation(@PathVariable int id, Model m) {

		if (!service.checkStudentIsNull(id)) {
			return "error";
		}

		service.configureStudentInformation(id, m);

		return "studentInformation";
	}

	@PostMapping("/grades")
	public String createGrade(@RequestParam("grade") double grade, @RequestParam("gradeType") String gradeType,
			@RequestParam("studentId") int studentId, Model m) {

		if (!service.checkStudentIsNull(studentId)) {
			return "error";
		}

		Boolean success = service.createGrade(grade, studentId, gradeType);
		if (!success) {
			return "error";
		}
		service.configureStudentInformation(studentId, m);

		return "studentInformation";
	}

	@GetMapping("/grades/{id}/{gradeType}")
	public String deleteGrade(@PathVariable("id") int id, @PathVariable("gradeType") String gradeType, Model m) {

		Integer studentId = service.deleteGrade(id, gradeType);

		if (studentId == 0) {
			return "error";
		}

		service.configureStudentInformation(studentId, m);

		return "studentInformation";
	}

}
