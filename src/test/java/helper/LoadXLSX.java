package helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LoadXLSX {
	private String path;
	private String sheetname;
	private XSSFWorkbook excelbook;
	private XSSFSheet excelsheet;
	
	public void loadXlsx() throws FileNotFoundException, IOException{
		GetProperties getproperties = new GetProperties();
		getproperties.loadPropertiesFile("resources/excel.properties");
		this.path = getproperties.getPropertyValue("invalidxlsx");
		this.sheetname = getproperties.getPropertyValue("invalidxlsxsheet");
		
		FileInputStream excelfile = new FileInputStream(path);
		excelbook = new XSSFWorkbook(excelfile);
		excelsheet = excelbook.getSheet(sheetname);
	}
	
	public String[][] getTestData(String tableName) {
		String[][] testData = null;

		try {
			DataFormatter formatter = new DataFormatter();
			XSSFCell[] boundaryCells = findCells(tableName);
			XSSFCell startCell = boundaryCells[0];
			XSSFCell endCell = boundaryCells[1];
			
			int startRow = startCell.getRowIndex() + 1;
			int endRow = endCell.getRowIndex() - 1;
			int startCol = startCell.getColumnIndex() + 1;
			int endCol = endCell.getColumnIndex() - 1;

			testData = new String[endRow - startRow + 1][endCol - startCol + 1];

			for (int i=startRow; i<endRow+1; i++) {
				for (int j=startCol; j<endCol+1; j++) {
					Cell cell = excelsheet.getRow(i).getCell(j);
					testData[i - startRow][j - startCol] = formatter.formatCellValue(cell);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return testData;
	}

	public XSSFCell[] findCells(String tableName) {
		DataFormatter formatter = new DataFormatter();
		String pos = "begin";
		XSSFCell[] cells = new XSSFCell[2];

		for (Row row : excelsheet) {
			for (Cell cell : row) {
				// if (tableName.equals(cell.getStringCellValue())) {
				if (tableName.equals(formatter.formatCellValue(cell))) {
					if (pos.equalsIgnoreCase("begin")) {
						// Find the begin cell, this is used for boundary cells
						cells[0] = (XSSFCell) cell;
						pos = "end";
					} else {
						// Find the end cell, this is used for boundary cells
						cells[1] = (XSSFCell) cell;
					}
				}
			}
		}
		// Return the cells array
		return cells;
	}
}