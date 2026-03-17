package com.luv2code.springmvc.service;

import java.util.Optional;
import java.util.function.BooleanSupplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.luv2code.springmvc.dao.MathGradeDao;
import com.luv2code.springmvc.dao.StudentDao;
import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.MathGrade;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class StudentAndGradeService {

    private final MathGradeDao mathGradeDao;

	@Autowired
	private StudentDao repo;
	
	@Autowired
	@Qualifier("mathGrades")
	private MathGrade mathGrade;

    StudentAndGradeService(MathGradeDao mathGradeDao) {
        this.mathGradeDao = mathGradeDao;
    }

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
		}
	}

	public Iterable<CollegeStudent> getGradeBook() {

		Iterable<CollegeStudent> data = repo.findAll();

		return data;
	}

	public Boolean createGrade(double grade, int studentId, String gradeType) {
		
		if(!checkStudentIsNull(studentId)) {
			return false;
		}
		
		if(grade >=0.0 && grade<= 100.0) {
			
			if(gradeType.equals("math")) {
				mathGrade.setId(0);
				mathGrade.setStudentId(studentId);
				mathGrade.setGrade(grade);
				mathGradeDao.save(mathGrade);
				return true;
				
			}
		}
		
		return false;
	}

}
