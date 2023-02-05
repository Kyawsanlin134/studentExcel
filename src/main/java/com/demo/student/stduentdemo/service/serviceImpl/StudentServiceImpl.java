package com.demo.student.stduentdemo.service.serviceImpl;

import com.demo.student.stduentdemo.dao.StudentDao;
import com.demo.student.stduentdemo.ds.ExcelHelper;
import com.demo.student.stduentdemo.ds.Student;
import com.demo.student.stduentdemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDao studentDao;

    @Override
    public Student saveStudent(Student student) {
        return studentDao.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDao.findAll();
    }
    @Override
    public Student getStudentById(Long id) {
        return studentDao.findById(id).get();
    }

    @Override
    public Student updateStudent(Student student, MultipartFile multipartFile) {
        return studentDao.save(student);
    }

    @Override
    public void deleteStudentById(long id) {
        studentDao.deleteById(id);
    }

    @Override
    public Student saveImage(Student student, MultipartFile multipartFile, String fileName) {
        return studentDao.save(student);
    }

    @Override
    public List<Student> findAllStudent(String keyword) {
        if (keyword !=null){
            return studentDao.findAll(keyword);
        }
        return studentDao.findAll();
    }

    @Override
    public void save(MultipartFile file) {
        try {
            List<Student> tutorials = ExcelHelper.excelToTutorials(file.getInputStream());
            studentDao.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

}



