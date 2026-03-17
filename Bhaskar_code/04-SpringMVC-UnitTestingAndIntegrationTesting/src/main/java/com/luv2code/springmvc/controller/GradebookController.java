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

		GradebookCollegeStudent studentInformation = service.studentInformation(id);

		if (studentInformation.getStudentGrades().getMathGradeResults().size() > 0) {
			m.addAttribute("mathAverage", studentInformation.getStudentGrades()
					.findGradePointAverage(studentInformation.getStudentGrades().getMathGradeResults()));
		} else {
			m.addAttribute("mathAverage", "N/A");
		}

		if (studentInformation.getStudentGrades().getScienceGradeResults().size() > 0) {
			m.addAttribute("scienceAverage", studentInformation.getStudentGrades()
					.findGradePointAverage(studentInformation.getStudentGrades().getScienceGradeResults()));
		} else {
			m.addAttribute("scienceAverage", "N/A");
		}

		if (studentInformation.getStudentGrades().getHistoryGradeResults().size() > 0) {
			m.addAttribute("historyAverage", studentInformation.getStudentGrades()
					.findGradePointAverage(studentInformation.getStudentGrades().getHistoryGradeResults()));
		} else {
			m.addAttribute("historyAverage", "N/A");
		}

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
		GradebookCollegeStudent studentInformation = service.studentInformation(studentId);

		if (studentInformation.getStudentGrades().getMathGradeResults().size() > 0) {
			m.addAttribute("mathAverage", studentInformation.getStudentGrades()
					.findGradePointAverage(studentInformation.getStudentGrades().getMathGradeResults()));
		} else {
			m.addAttribute("mathAverage", "N/A");
		}

		if (studentInformation.getStudentGrades().getScienceGradeResults().size() > 0) {
			m.addAttribute("scienceAverage", studentInformation.getStudentGrades()
					.findGradePointAverage(studentInformation.getStudentGrades().getScienceGradeResults()));
		} else {
			m.addAttribute("scienceAverage", "N/A");
		}

		if (studentInformation.getStudentGrades().getHistoryGradeResults().size() > 0) {
			m.addAttribute("historyAverage", studentInformation.getStudentGrades()
					.findGradePointAverage(studentInformation.getStudentGrades().getHistoryGradeResults()));
		} else {
			m.addAttribute("historyAverage", "N/A");
		}

		return "studentInformation";
	}

}
