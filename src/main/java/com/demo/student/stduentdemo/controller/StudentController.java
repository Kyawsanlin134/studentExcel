package com.demo.student.stduentdemo.controller;

import com.demo.student.stduentdemo.dao.InterestDao;
import com.demo.student.stduentdemo.dao.StudentDao;
import com.demo.student.stduentdemo.dao.StudentResultsRepository;
import com.demo.student.stduentdemo.ds.*;
import com.demo.student.stduentdemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private InterestDao interestDao;
    @Autowired
    private StudentResultsRepository studentResultsRepository;

    @GetMapping("/")
    public String createStudentForm(Model model) {

        Student student = new Student();
        model.addAttribute("student", student);

        List<Interest> listInterest = interestDao.findAll();
        model.addAttribute("listInterest", listInterest);

        return "index";
    }
    @PostMapping("/student")
    public String saveStudent(@RequestParam(value = "file") MultipartFile multipartFile,
                              @ModelAttribute("student") Student student,@ModelAttribute StudentResults studentResults,
                               Model model, HttpServletRequest request) throws IOException {

        String[] subjects = request.getParameterValues("subject");
        String[] marks = request.getParameterValues("mark");
        String[] grades = request.getParameterValues("grade");
        String[] years = request.getParameterValues("year");

        for (int i = 0; i < subjects.length; i++) {
            student.addResults(subjects[i], marks[i], grades[i], years[i]);
        }

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        student.setImage(fileName);
        Student submitStudent = studentService.saveStudent(student);
        String uploadDir = "./src/main/resources/static";
        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        try(InputStream inputStream = multipartFile.getInputStream()) {

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        }catch (IOException e){
            throw new IOException("Could not save uploaded file :" + fileName);
        }
        studentService.saveStudent(student);
        return "redirect:/students";

    }
    @GetMapping("/students")
    public String studentList(Model model){
        model.addAttribute("students",studentService.getAllStudents());
        return "studentlist";
    }
    @GetMapping("/students/edit/{id}")

    public String editStudentFrom(@PathVariable Long id, Model model){
    model.addAttribute("student",studentService.getStudentById(id));

    List<Interest> listInterest = interestDao.findAll();
    model.addAttribute("listInterest", listInterest);

    return "editstudent";
    }

@PostMapping("/students/{id}")
public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student student,
                            RedirectAttributes redirectAttributes, @RequestParam("file") MultipartFile multipartFile,
                            HttpServletRequest request) throws IOException {


    List<Integer> stud_results = studentResultsRepository.findStudentResultsById(id);
    for (Integer a : stud_results) {
        studentResultsRepository.deleteById(a);
    }

    System.out.println(request.getContentLength());
    String[] subjects = null;

    if (request.getContentLength() == 0 && subjects == null) {
        studentService.saveStudent(student);
    } else {
        String[] resultsIds = request.getParameterValues("resultsId");

        subjects = request.getParameterValues("subject");

        String[] marks = request.getParameterValues("mark");

        String[] grades = request.getParameterValues("grade");

        String[] years = request.getParameterValues("year");


        if (null != subjects && subjects.length > 0) {

            int size = subjects.length;

            for (int i = 0; i < size; i++) {

                if (resultsIds != null && resultsIds.length > 0) {

                    student.addResults(subjects[i], marks[i], grades[i], years[i]);

                } else if (resultsIds == null) {
                    student.addResults(subjects[i], marks[i], grades[i], years[i]);
                    ;
                }

            }
        }

    }
    Student existingStudent = studentService.getStudentById(id);
    if (!multipartFile.isEmpty()) {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        student.setImage(fileName);
        Student updStudent = studentService.saveStudent(student);

        String uploadDir = "/src/main/resources/static/" + updStudent.getId();
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectory(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException io) {
            throw new IOException("cannot save" + fileName);
        }
        studentService.saveImage(updStudent, multipartFile, fileName);
    } else {
        String imgPath = existingStudent.getImage();
        student.setImage(imgPath);
        Student editImgStudent = studentService.updateStudent(student, multipartFile);
        studentService.saveImage(editImgStudent, multipartFile, imgPath);
    }
    return"redirect:/students";
}
    @GetMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return "redirect:/students";
    }
    @GetMapping("/search")
    public String getStudent(@Param("keyword") String keyword, Model model){
        List<Student> students = studentService.findAllStudent(keyword);
        model.addAttribute("students",students);
        model.addAttribute("keyword",keyword);
        return "studentlist";
    }
    @GetMapping("/students/export")
    public void exportToExcel(HttpServletResponse response)throws IOException {

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename= students.xls";

        response.setHeader(headerKey, headerValue);
        List<Student> listStudents = studentService.getAllStudents();

        StudentExcelExporter excelExporter = new StudentExcelExporter(listStudents);
        excelExporter.export(response);

    }
    @PostMapping("/students/import")
    public String uploadFile(@RequestParam("file") MultipartFile file) {

        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                studentService.save(file);


                return "redirect:/students";
            } catch (Exception e) {
                return "Could not upload the file: " + file.getOriginalFilename() + "!";
            }
        }

        return "Please upload an excel file!";
    }

}
