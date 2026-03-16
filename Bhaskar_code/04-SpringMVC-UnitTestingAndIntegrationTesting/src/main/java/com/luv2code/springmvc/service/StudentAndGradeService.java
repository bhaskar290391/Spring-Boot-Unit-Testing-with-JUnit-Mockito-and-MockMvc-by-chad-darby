package com.luv2code.springmvc.service;

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
		repo.save(studnet);
	}

}
