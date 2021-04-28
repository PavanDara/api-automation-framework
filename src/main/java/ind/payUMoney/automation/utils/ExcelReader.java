package ind.payUMoney.automation.utils;


import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;

public class ExcelReader {

    private XSSFWorkbook workbook;
    private XSSFRow row;

    public String readTestData(String filePath, int column_value) throws IOException {
            workbook = new XSSFWorkbook(filePath); // or: new XSSFWorkbook() - depending on excel version
        XSSFSheet sourceSheet = workbook.getSheet("sheet1");
        int row_no=sourceSheet.getLastRowNum();
            row = sourceSheet.getRow(1);
            return row.getCell(column_value).getStringCellValue();
    }
}
