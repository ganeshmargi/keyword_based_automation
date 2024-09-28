import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {
    private String filePath;

    public ExcelUtil(String filePath) {
        this.filePath = filePath;
    }

    public List<String[]> readTestData(String sheetName) throws IOException {
        List<String[]> data = new ArrayList<>();
        FileInputStream fis = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        // Skip header row and read the data
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue; // Skip empty rows
            int cellCount = row.getPhysicalNumberOfCells();
            String[] rowData = new String[cellCount];
            for (int j = 0; j < cellCount; j++) {
                Cell cell = row.getCell(j);
                if (cell != null) {
                    switch (cell.getCellType()) {
                        case STRING:
                            rowData[j] = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            rowData[j] = String.valueOf(cell.getNumericCellValue());
                            break;
                        case BOOLEAN:
                            rowData[j] = String.valueOf(cell.getBooleanCellValue());
                            break;
                        default:
                            rowData[j] = "";
                    }
                } else {
                    rowData[j] = ""; // Handle empty cells
                }
            }
            data.add(rowData);
        }

        workbook.close();
        fis.close();
        return data;
    }
}
