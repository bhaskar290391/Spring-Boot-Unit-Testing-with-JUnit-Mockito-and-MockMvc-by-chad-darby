package com.luv2code.springmvc.service;

import java.util.Optional;
import java.util.function.BooleanSupplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.springmvc.dao.StudentDao;
import com.luv2code.springmvc.models.CollegeStudent;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class StudentAndGradeService {

	@Autowired
	private StudentDao repo;

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

}
