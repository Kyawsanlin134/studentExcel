package com.demo.student.stduentdemo.ds;

import javax.persistence.*;

@Entity
@Table(name = "stud_results_tb")
public class StudentResults {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "subject", nullable = false)
    private String subject;
    @Column(name = "mark", nullable = false)
    private String mark;
    @Column(name = "grade", nullable = false)
    private String grade;
    @Column(name = "year", nullable = false)
    private String year;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;


    public StudentResults() {
    }

    public StudentResults(Integer id, String subject, String mark, String grade, String year, Student student) {
        this.id = id;
        this.subject = subject;
        this.mark = mark;
        this.grade = grade;
        this.year = year;
        this.student = student;
    }

    public StudentResults(String subject, String mark, String grade, String year, Student student) {
        this.subject = subject;
        this.mark = mark;
        this.grade = grade;
        this.year = year;
        this.student = student;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    @Override
    public String toString() {
        return "StudentResults{" +
                "subject='" + subject + '\'' +
                ", mark='" + mark + '\'' +
                ", grade='" + grade + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
