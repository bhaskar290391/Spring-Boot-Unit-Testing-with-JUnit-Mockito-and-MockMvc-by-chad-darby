package com.luv2code.springmvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luv2code.springmvc.models.MathGrade;

public interface MathGradeDao extends JpaRepository<MathGrade, Integer> {

	Iterable<MathGrade> findGradeByStudentId(int id);

}
