package com.quanlykho.inventory_user;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.quanlykho.AbstractExporter;
import com.quanlykho.common.InventoryUser;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class InventoryUserExcelExport extends AbstractExporter {
     private XSSFWorkbook workBook;
     private XSSFSheet sheet;
     
     public InventoryUserExcelExport() {
    	 workBook = new XSSFWorkbook();
     }
     
     private void writeHeaderline() {
    	 sheet = workBook.createSheet("InventoryUsers");
    	 XSSFRow row = sheet.createRow(0);
    	 XSSFCellStyle cellStyle = workBook.createCellStyle();
    	 XSSFFont font = workBook.createFont();
    	 font.setBold(true);
    	 font.setFontHeight(16);
    	 cellStyle.setFont(font);
    	 createCell(row, 0,"User id", cellStyle);
    	 createCell(row, 1,"Identity Number",cellStyle);
    	 createCell(row, 2,"Full Name", cellStyle);
    	 createCell(row, 3,"Address",cellStyle);
    	 createCell(row, 4,"Phone number",cellStyle);
    	 createCell(row, 5,"Email",cellStyle);
    	 
     }
     private void createCell(XSSFRow row, int columnIndex,Object value, CellStyle cellStyle) {
 		XSSFCell cell = row.createCell(columnIndex);
 		if(value instanceof Integer) {
 			cell.setCellValue((Integer)value);
 		}else if(value instanceof Boolean) {
 			cell.setCellValue((Boolean)value);
 		}else {
 			cell.setCellValue((String)value);
 		}
 		
 		cell.setCellStyle(cellStyle);
 	}
    
     public void export(List<InventoryUser> listUser,HttpServletResponse response) throws IOException {
 		super.setResponseHeader(response, "application/octet-stream", ".xlsx","inventoryusers_");
 		//name of sheet
 		writeHeaderline();
 		writeDataLines(listUser);
 		
 	
 		
 		//write to sheet
 		ServletOutputStream outputStream = response.getOutputStream();
 		workBook.write(outputStream);
 		workBook.close();
 		outputStream.close();
 	} 
     
    private void writeDataLines(List<InventoryUser> listUser) {
    	int rowIndex = 1;
    	XSSFCellStyle cellStyle = workBook.createCellStyle();
		XSSFFont font = workBook.createFont();
		font.setFontHeight(14);
		cellStyle.setFont(font);
		for(InventoryUser user : listUser) {
			XSSFRow row = sheet.createRow(rowIndex++);
			int collumIndex =0;
			createCell(row,collumIndex++,user.getUserId(),cellStyle);
			createCell(row,collumIndex++,user.getIdentityNumber(),cellStyle);
			createCell(row,collumIndex++,user.getFullName(),cellStyle);
			createCell(row,collumIndex++,user.getAddress(),cellStyle);
			createCell(row,collumIndex++,user.getPhoneNumber(), cellStyle);
			createCell(row,collumIndex++,user.getEmail(),cellStyle);
		}
    } 
}
