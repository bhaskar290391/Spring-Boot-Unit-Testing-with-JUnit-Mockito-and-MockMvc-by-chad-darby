package com.luv2code.springmvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luv2code.springmvc.models.HistoryGrade;

public interface HistoryGradeDao extends JpaRepository<HistoryGrade, Integer> {
	Iterable<HistoryGrade> findGradeByStudentId(int id);
}
