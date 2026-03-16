package com.luv2code.springmvc.controller;

import com.luv2code.springmvc.models.*;
import com.luv2code.springmvc.service.StudentAndGradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GradebookController {

	private final CollegeStudent getCollegeStudent;

	@Autowired
	private Gradebook gradebook;

	@Autowired
	private StudentAndGradeService service;

	GradebookController(CollegeStudent getCollegeStudent) {
		this.getCollegeStudent = getCollegeStudent;
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
		return "studentInformation";
	}

}
