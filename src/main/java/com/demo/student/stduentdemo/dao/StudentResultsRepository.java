package com.demo.student.stduentdemo.dao;

import com.demo.student.stduentdemo.ds.StudentResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentResultsRepository extends JpaRepository<StudentResults,Integer> {


    public List<StudentResults>deleteStudentResultsById(@Param("rId") Long rId);

    @Query(value = "select stuResult.id from StudentResults stuResult where stuResult.student.id=:id")

     public List <Integer> findStudentResultsById(Long id);
      public StudentResults getStudentResultsById(Long id);


}
