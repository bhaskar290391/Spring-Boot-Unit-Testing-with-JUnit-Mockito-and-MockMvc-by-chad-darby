package com.luv2code.springmvc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.luv2code.springmvc.dao.HistoryGradeDao;
import com.luv2code.springmvc.dao.MathGradeDao;
import com.luv2code.springmvc.dao.ScienceGradeDao;
import com.luv2code.springmvc.dao.StudentDao;
import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.Grade;
import com.luv2code.springmvc.models.GradebookCollegeStudent;
import com.luv2code.springmvc.models.HistoryGrade;
import com.luv2code.springmvc.models.MathGrade;
import com.luv2code.springmvc.models.ScienceGrade;
import com.luv2code.springmvc.models.StudentGrades;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class StudentAndGradeService {

	@Autowired
	private MathGradeDao mathGradeDao;

	@Autowired
	private ScienceGradeDao scienceGradeDao;

	@Autowired
	private HistoryGradeDao historyGradeDao;

	@Autowired
	private StudentDao repo;

	@Autowired
	@Qualifier("mathGrades")
	private MathGrade mathGrade;

	@Autowired
	@Qualifier("scienceGrades")
	private ScienceGrade scienceGrade;

	@Autowired
	@Qualifier("historyGrades")
	private HistoryGrade historyGrade;

	@Autowired
	private StudentGrades studentGrades;

	public void createStudent(String firstName, String lastName, String email) {

		CollegeStudent studnet = new CollegeStudent(firstName, lastName, email);
		studnet.setId(0);
		repo.save(studnet);
	}

	public Boolean checkStudentIsNull(int id) {

		Optional<CollegeStudent> byId = repo.findById(id);

		if (byId.isPresent()) {
			return true;

		} else {
			return false;
		}
	}

	public void deleteStudent(int id) {

		if (checkStudentIsNull(id)) {
			repo.deleteById(id);
			mathGradeDao.deleteByStudentId(id);
			scienceGradeDao.deleteByStudentId(id);
			historyGradeDao.deleteByStudentId(id);
		}
	}

	public Iterable<CollegeStudent> getGradeBook() {

		Iterable<CollegeStudent> data = repo.findAll();

		return data;
	}

	public Boolean createGrade(double grade, int studentId, String gradeType) {

		if (!checkStudentIsNull(studentId)) {
			return false;
		}

		if (grade >= 0.0 && grade <= 100.0) {

			if (gradeType.equals("math")) {
				mathGrade.setId(0);
				mathGrade.setStudentId(studentId);
				mathGrade.setGrade(grade);
				mathGradeDao.save(mathGrade);
				return true;

			}

			if (gradeType.equals("science")) {
				scienceGrade.setId(0);
				scienceGrade.setStudentId(studentId);
				scienceGrade.setGrade(grade);
				scienceGradeDao.save(scienceGrade);
				return true;

			}

			if (gradeType.equals("history")) {
				historyGrade.setId(0);
				historyGrade.setStudentId(studentId);
				historyGrade.setGrade(grade);
				historyGradeDao.save(historyGrade);
				return true;

			}
		}

		return false;
	}

	public Integer deleteGrade(int gradeId, String gradeType) {
		int studentId = 0;

		if (gradeType.equals("math")) {

			Optional<MathGrade> gradeData = mathGradeDao.findById(gradeId);

			if (gradeData.isEmpty()) {
				return studentId;
			}

			studentId = gradeData.get().getStudentId();
			mathGradeDao.deleteById(gradeId);

		}
		if (gradeType.equals("science")) {
			Optional<ScienceGrade> gradeData = scienceGradeDao.findById(gradeId);

			if (gradeData.isEmpty()) {
				return studentId;
			}

			studentId = gradeData.get().getStudentId();
			scienceGradeDao.deleteById(gradeId);

		}
		if (gradeType.equals("history")) {
			Optional<HistoryGrade> gradeData = historyGradeDao.findById(gradeId);

			if (gradeData.isEmpty()) {
				return studentId;
			}

			studentId = gradeData.get().getStudentId();
			historyGradeDao.deleteById(gradeId);

		}
		return studentId;
	}

	public GradebookCollegeStudent studentInformation(int studentId) {
		if (!checkStudentIsNull(studentId)) {
			return null;
		}

		Optional<CollegeStudent> students = repo.findById(studentId);
		Iterable<MathGrade> mathGradeByStudentId = mathGradeDao.findGradeByStudentId(studentId);
		Iterable<ScienceGrade> scienceGradeByStudentId = scienceGradeDao.findGradeByStudentId(studentId);
		Iterable<HistoryGrade> historyGradeByStudentId = historyGradeDao.findGradeByStudentId(studentId);

		List<Grade> math = new ArrayList<>();
		List<Grade> science = new ArrayList<>();
		List<Grade> history = new ArrayList<>();

		mathGradeByStudentId.forEach(math::add);
		scienceGradeByStudentId.forEach(science::add);
		historyGradeByStudentId.forEach(history::add);

		studentGrades.setHistoryGradeResults(history);
		studentGrades.setMathGradeResults(math);
		studentGrades.setScienceGradeResults(science);

		GradebookCollegeStudent gradebookCollegeStudent = new GradebookCollegeStudent(students.get().getId(),
				students.get().getFirstname(), students.get().getLastname(), students.get().getEmailAddress(),
				studentGrades);

		return gradebookCollegeStudent;
	}

}
