package com.demo.student.stduentdemo.ds;


import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.http.Part;

@Entity
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nrc;
    private String gender;
    //    @DateTimeFormat(pattern = "mm/dd/yyyy")
    private String db;
    private String city;
    private String address;
    private String image;
    private String remark;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "student_interest",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    private Set<Interest> interest = new HashSet<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentResults> resultsList = new ArrayList<>();

    public Student() {
        super();
    }

    public Student(String name, String nrc, String gender,String db, String city, String address, String image, String remark) {
        this.name = name;
        this.nrc = nrc;
        this.gender = gender;
        this.db = db;
        this.city = city;
        this.address = address;
        this.image = image;
        this.remark = remark;
    }

    public Set<Interest> getInterest() {
        return interest;
    }


    public List<StudentResults> getResultsList() {
        return resultsList;
    }

    public Student(List<StudentResults> resultsList) {
        this.resultsList = resultsList;
    }

    public void setResultsList(List<StudentResults> resultsList) {
        this.resultsList = resultsList;
    }

    public void addResults(String subject,String mark,String grade,String year){
        this.resultsList.add(new StudentResults(subject,mark,grade,year,this));
    }

    public Student(Set<Interest> interests) {
        this.interest = interests;
    }

    public Set<Interest> getInterests() {
        return interest;
    }

    public void setInterests(Set<Interest> interests) {
        this.interest = interests;
    }

    public void addHobby(Interest interest) {
        this.interest.add(interest);
    }

    public void removeHobby(Interest interest) {
        this.interest.remove(interest);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Transient
    public String getImagePath(){
        if (image == null || getId() == 0 )
            return null;
        return "/" + image;
    }



}

