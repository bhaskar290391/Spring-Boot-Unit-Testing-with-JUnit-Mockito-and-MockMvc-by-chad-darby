package com.luv2code.springmvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luv2code.springmvc.models.ScienceGrade;

public interface ScienceGradeDao extends JpaRepository<ScienceGrade, Integer> {

	Iterable<ScienceGrade> findGradeByStudentId(int id);
}
