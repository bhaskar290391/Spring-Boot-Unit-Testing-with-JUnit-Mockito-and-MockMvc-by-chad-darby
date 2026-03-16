package com.luv2code.springmvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luv2code.springmvc.models.CollegeStudent;

@Repository
public interface StudentDao extends JpaRepository<CollegeStudent, Integer> {

	public CollegeStudent findByEmailAddress(String emailAddress);

}
