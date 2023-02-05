package com.demo.student.stduentdemo.dao;

import com.demo.student.stduentdemo.ds.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestDao extends JpaRepository<Interest, Integer> {

}
