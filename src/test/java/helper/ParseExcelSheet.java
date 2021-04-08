package helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ParseExcelSheet {
	public static String[] parseSheet(String fileloc, String param) throws IOException {
		FileInputStream excelFile = new FileInputStream(new File(fileloc));
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet datatypeSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = datatypeSheet.iterator();
		int rowsNumber = datatypeSheet.getLastRowNum() + 1;
		String arrSeller[];
		arrSeller = new String[rowsNumber - 1];
		String arrBuyer[];
		arrBuyer = new String[rowsNumber - 1];
		int i = 0;
		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			Cell cellSeller = currentRow.getCell(4);
			Cell cellBuyer = currentRow.getCell(6);
			if (!cellSeller.toString().equals("Seller") && !cellBuyer.toString().equals("Buyer")) {
				arrSeller[i] = cellSeller.toString();
				arrBuyer[i] = cellBuyer.toString();
				i++;
			}

		}
		workbook.close();
		if (param.equals("Seller")) {
			return arrSeller;
		} else if (param.equals("Buyer")) {
			return arrBuyer;
		} else {
			return null;
		}
	}
}
