package com.company.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.company.entity.Employee;
import com.company.form.EmployeeFilterForm;
import com.company.repository.IEmployeeRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExcelService {
    @Autowired
    private IEmployeeRepository employeeRepository;
    public static final int COLUMN_INDEX_EMPLOYEE_NUMBER        = 0;
    public static final int COLUMN_INDEX_NAME     = 1;
    public static final int COLUMN_INDEX_PHONE_NUMBER      = 2;
    public static final int COLUMN_INDEX_POSITION  = 3;
    public static final int COLUMN_INDEX_EMAIL     = 4;
    private static CellStyle cellStyleFormatNumber = null;

    public  void writeExcel( String excelFilePath) throws IOException {
        List<Employee> employees = employeeRepository.getAllEmployees(new EmployeeFilterForm());
        // Create Workbook
        Workbook workbook = getWorkbook(excelFilePath);

        // Create sheet
        Sheet sheet = workbook.createSheet("Employees"); // Create sheet with sheet name

        int rowIndex = 0;

        // Write header
        writeHeader(sheet, rowIndex);

        // Write data
        rowIndex++;
        for (Employee employee : employees) {
            // Create row
            Row row = sheet.createRow(rowIndex);
            // Write data on row
            writeBook(employee, row);
            rowIndex++;
        }


        // Auto resize column witdth
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        autosizeColumn(sheet, numberOfColumn);

        // Create file excel
        createOutputFile(workbook, excelFilePath);
        System.out.println("Done!!!");
    }
    // Create workbook
    private static Workbook getWorkbook(String excelFilePath) throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }
    // Write data
    private static void writeBook(Employee employee, Row row) {
        if (cellStyleFormatNumber == null) {
            // Format number
            short format = (short)BuiltinFormats.getBuiltinFormat("#,##0");
            // DataFormat df = workbook.createDataFormat();
            // short format = df.getFormat("#,##0");

            //Create CellStyle
            Workbook workbook = row.getSheet().getWorkbook();
            cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setDataFormat(format);
        }

        Cell cell = row.createCell(COLUMN_INDEX_EMPLOYEE_NUMBER);
        cell.setCellValue(employee.getEmployee_number());

        cell = row.createCell(COLUMN_INDEX_NAME);
        cell.setCellValue(employee.getName());

        cell = row.createCell(COLUMN_INDEX_EMAIL);
        cell.setCellValue(employee.getEmail());

        cell = row.createCell(COLUMN_INDEX_PHONE_NUMBER);
        cell.setCellValue(employee.getPhone_number());

        cell = row.createCell(COLUMN_INDEX_POSITION);
        cell.setCellValue(employee.getPosition());

    }
    // Write header with format
    private static void writeHeader(Sheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);

        // Create row
        Row row = sheet.createRow(rowIndex);

        // Create cells
        Cell cell = row.createCell(COLUMN_INDEX_EMPLOYEE_NUMBER);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Employee Number");

        cell = row.createCell(COLUMN_INDEX_NAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Name");

        cell = row.createCell(COLUMN_INDEX_EMAIL);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Email");

        cell = row.createCell(COLUMN_INDEX_PHONE_NUMBER);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Phone Number");

        cell = row.createCell(COLUMN_INDEX_POSITION);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Position");

    }
    // Create CellStyle for header
    private static CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }

    // Auto resize column width
    private static void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }

    // Create output file
    private static void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
        try (OutputStream os = new FileOutputStream(excelFilePath)) {
            workbook.write(os);
        }
    }
}
