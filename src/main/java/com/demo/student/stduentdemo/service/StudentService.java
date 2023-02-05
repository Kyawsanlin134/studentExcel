package com.demo.student.stduentdemo.service;

import com.demo.student.stduentdemo.dao.StudentDao;
import com.demo.student.stduentdemo.ds.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface StudentService {

    Student saveStudent(Student student);

    List<Student> getAllStudents();

    Student getStudentById(Long id);

    Student updateStudent(Student student, MultipartFile multipartFile);

    void deleteStudentById(long id);
    public Student saveImage(Student student,MultipartFile multipartFile,String fileName);

    public List<Student> findAllStudent(String keyword);

    public void save(MultipartFile file);


}
