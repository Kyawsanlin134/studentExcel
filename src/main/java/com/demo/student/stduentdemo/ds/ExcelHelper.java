package com.demo.student.stduentdemo.ds;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExcelHelper {
	

	  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	  static String[] HEADERs = { "Id", "Name", "Gender", "NRC", "DateofBirth", "Email", "City", "Address", "Hobby", "Remark" };
	  static String SHEET = "students";

	  public static boolean hasExcelFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }

	    return true;
	  }

	  public static List<Student> excelToTutorials(InputStream is) {
	    try {
	      Workbook workbook = new XSSFWorkbook(is);

	      Sheet sheet = workbook.getSheet(SHEET);
	      Iterator<Row> rows = sheet.iterator();

	      List<Student> students = new ArrayList<Student>();

	      int rowNumber = 0;
	      while (rows.hasNext()) {
	        Row currentRow = rows.next();

	        // skip header
	        if (rowNumber == 0) {
	          rowNumber++;
	          continue;
	        }

	        Iterator<Cell> cellsInRow = currentRow.iterator();

	        Student student = new Student();

	        int cellIdx = 0;
	        while (cellsInRow.hasNext()) {
	          Cell currentCell = cellsInRow.next();

	          switch (cellIdx) {
	          case 0:
	        	  student.setId((long) currentCell.getNumericCellValue());
	            break;

	          case 1:
	        	  student.setName(currentCell.getStringCellValue());
	            break;

	          case 2:
	        	  student.setGender(currentCell.getStringCellValue());
	            break;

	          case 3:
	        	  student.setNrc(currentCell.getStringCellValue());
	            break;
	            
	          case 4:
	        	  student.setDb(currentCell.getStringCellValue());
	        	  break;

	          case 5:
	        	  student.setCity(currentCell.getStringCellValue());
	        	  break;
	        	  
	          case 6:
	        	  student.setAddress(currentCell.getStringCellValue());
	        	  break;
	          case 7:
	        	  student.setRemark(currentCell.getStringCellValue());
	        	  break;

	          default:
	            break;
	          }

	          cellIdx++;
	        }

	        students.add(student);
	      }

	      workbook.close();

	      return students;
	    } catch (IOException e) {
	      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
	    }
	  }

}