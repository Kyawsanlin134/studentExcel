package com.demo.student.stduentdemo.ds;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class StudentExcelExporter {

	private XSSFWorkbook workBook;
	private XSSFSheet sheet;
	
	private List<Student> listStudents;
	
	
	public StudentExcelExporter(List<Student> listStudents) {
		this.listStudents = listStudents;
		
		workBook = new XSSFWorkbook();
		sheet = workBook.createSheet("Students");
	}
	
	
	public void writeHeaderRow() {
		Row row = sheet.createRow(0);
		
		CellStyle style = workBook.createCellStyle();
		XSSFFont font = workBook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		
		Cell cell = row.createCell(0);
		cell.setCellValue("Student ID");
		cell.setCellStyle(style);
		
		cell = row.createCell(1);
		cell.setCellValue("Name");
		cell.setCellStyle(style);

		cell = row.createCell(2);
		cell.setCellValue("Gender");
		cell.setCellStyle(style);

		cell = row.createCell(3);
		cell.setCellValue("NRC");
		cell.setCellStyle(style);

		cell = row.createCell(4);
		cell.setCellValue("Date of Birth");
		cell.setCellStyle(style);

		cell = row.createCell(5);
		cell.setCellValue("City");
		cell.setCellStyle(style);
		
		cell = row.createCell(6);
		cell.setCellValue("Address");
		cell.setCellStyle(style);

		cell = row.createCell(7);
		cell.setCellValue("Rmark");
		cell.setCellStyle(style);

	}

	public void writeDataRows() {
		int rowCount = 1;
		
		CellStyle style = workBook.createCellStyle();
		XSSFFont font = workBook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		
		for(Student student: listStudents) {

			Row row = sheet.createRow(rowCount++);
			
			Cell cell = row.createCell(0);
			cell.setCellValue(student.getId());
			sheet.autoSizeColumn(0);
			cell.setCellStyle(style);
			
			cell = row.createCell(1);
			cell.setCellValue(student.getName());
			sheet.autoSizeColumn(1);
			cell.setCellStyle(style);
			
			cell = row.createCell(2);
			cell.setCellValue(student.getGender());
			sheet.autoSizeColumn(2);
			cell.setCellStyle(style);
			
			cell = row.createCell(3);
			cell.setCellValue(student.getNrc());
			sheet.autoSizeColumn(3);
			cell.setCellStyle(style);
			
		    cell = row.createCell(4);
			cell.setCellValue(student.getDb().toString());
			sheet.autoSizeColumn(4);
			cell.setCellStyle(style);
			
			cell = row.createCell(5);
			cell.setCellValue(student.getCity());
			sheet.autoSizeColumn(5);
			cell.setCellStyle(style);
			
			cell = row.createCell(6);
			cell.setCellValue(student.getAddress());
			sheet.autoSizeColumn(6);
			cell.setCellStyle(style);
			
			cell = row.createCell(7);
			cell.setCellValue(student.getRemark());
			sheet.autoSizeColumn(7);
			cell.setCellStyle(style);

		}
	}
	public void export(HttpServletResponse response)throws IOException {
		writeHeaderRow();
		writeDataRows();
		
		ServletOutputStream outputStream = response.getOutputStream();
		workBook.write(outputStream);
		workBook.close();
		outputStream.close();
	}
}
