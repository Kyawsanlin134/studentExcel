package com.demo.student.stduentdemo;

import com.demo.student.stduentdemo.ds.Student;
import com.demo.student.stduentdemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Date;

@SpringBootApplication
public class StduentdemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(StduentdemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
