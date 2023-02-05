package com.demo.student.stduentdemo.dao;

import com.demo.student.stduentdemo.ds.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentDao extends JpaRepository<Student,Long> {
    @Query("select stu from Student stu where stu.name like %?1% or stu.remark like %?1% or stu.city like %?1% or stu.gender like %?1% or stu.address like %?1% or stu.nrc like %?1%")

    public List<Student> findAll(String keyword);
}
