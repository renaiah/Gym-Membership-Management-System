package OperatinsOnEmployee;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;



public class Main {

    public static void main(String[] args) {
        ArrayList<EmployeeWorkLog> employees = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            String empid = ReadExcel("Sheet1", i, 0);
            String name = ReadExcel("Sheet1", i, 1);
            String dept = ReadExcel("Sheet1", i, 2);
            String projectId = ReadExcel("Sheet1", i, 3);
            String dateStr = ReadExcel("Sheet1", i, 4);
            String task = ReadExcel("Sheet1", i, 5);
            String hoursWorkedStr = ReadExcel("Sheet1", i, 6);
            String remarks = ReadExcel("Sheet1", i, 7);

            LocalDate date = dateStr.isEmpty() ? null : LocalDate.parse(dateStr);
            double hoursWorked = hoursWorkedStr.isEmpty() ? 0.0 : Double.parseDouble(hoursWorkedStr);
            EmployeeWorkLog e = new EmployeeWorkLog(empid, name, dept, projectId, date, task, hoursWorked, remarks);
            employees.add(e);
        }
        
        //All employee records
//        for (Employee e : employees) {
//            System.out.println(e);
//        }
//        OperationsOnEmployees opeOnEmp = new OperationsOnEmployees();
//        opeOnEmp.employeeAndMonthAverageWeeklyHours(employees);
//        opeOnEmp.heighestHoursWorkedEmpsGivenDays(employees);
//        opeOnEmp.criticalProjectsTimeAndCount(employees);
//        opeOnEmp.sortByDeptProjectDateCategory(employees);

        OperationsOnEmployees opeOnEmp = new OperationsOnEmployees();

//        calling operation methods
        Map<String, Map<String, Double>> avgHours = opeOnEmp.employeeAndMonthAverageWeeklyHours(employees);
        int row = 0;
        WriteExcel("Sheet2", row, 0, "Emp ID");
        WriteExcel("Sheet2", row, 1, "Month");
        WriteExcel("Sheet2", row, 2, "Avg Weekly Hours");
        row++;

        for (String empId : avgHours.keySet()) {
            for (String month : avgHours.get(empId).keySet()) {
                double avg = avgHours.get(empId).get(month) / 4.0;
                WriteExcel("Sheet2", row, 0, empId);
                WriteExcel("Sheet2", row, 1, month);
                WriteExcel("Sheet2", row, 2, String.format("%.2f", avg));
                row++;
            }
        }

        row += 2;
        WriteExcel("Sheet2", row++, 0, "Top 5 Employees (Last 60 Days)");
        WriteExcel("Sheet2", row, 0, "Emp ID");
        WriteExcel("Sheet2", row, 1, "Name");
        WriteExcel("Sheet2", row, 2, "Hours");
        row++;

        List<EmployeeWorkLog> top5 = opeOnEmp.heighestHoursWorkedEmpsGivenDays(employees);
        for (EmployeeWorkLog e : top5) {
            WriteExcel("Sheet2", row, 0, e.getEmployeeId());
            WriteExcel("Sheet2", row, 1, e.getName());
            WriteExcel("Sheet2", row, 2, String.valueOf(e.getHoursWorked()));
            row++;
        }
        
        row += 2;
        WriteExcel("Sheet2", row++, 0, "Critical Projects (More than 2 Employees & 10+ Total Hours)");

        // Column headers
        WriteExcel("Sheet2", row, 0, "Project");
        WriteExcel("Sheet2", row, 1, "Employees");
        WriteExcel("Sheet2", row++, 2, "Total Hours");

        // Writing actual data
        List<List<String>> criticalProjects = opeOnEmp.criticalProjectsTimeAndCount(employees);
        for (List<String> proj : criticalProjects) {
            int col = 0;
            for (String val : proj) {
                WriteExcel("Sheet2", row, col++, val);
            }
            row++;
        }

//        row += 2;
//        WriteExcel("Sheet2", row++, 0, "Critical Projects (More than 2 Employees & 10+ Total Hours)");
//        List<String> criticalProjects = opeOnEmp.criticalProjectsTimeAndCount(employees);
//        for (String line : criticalProjects) {
//            WriteExcel("Sheet2", row++, 0, line);
//        }
        
        

        row += 2;
        WriteExcel("Sheet2", row++, 0, "Sorted Employee Logs by Dept → Project → Date");
        List<String> sortedEmployeeLogs = opeOnEmp.sortByDeptProjectDateCategory(employees);
        for (String line : sortedEmployeeLogs) {
            WriteExcel("Sheet2", row++, 0, line);
        }


    }
    

    public static String ReadExcel(String SheetName, int rNum, int cNum) {
        String data = "";

        try (FileInputStream fis = new FileInputStream("E:\\Medplus Training\\Java Training\\Sample_Employee_WorkLogs.xlsx")) {
            Workbook wb = WorkbookFactory.create(fis);
            Sheet s = wb.getSheet(SheetName);
            if (s == null) 
            	return "";
            Row r = s.getRow(rNum);
            if (r == null) 
            	return "";
            Cell c = r.getCell(cNum);
            if (c == null) 
            	return "";

            switch (c.getCellType()) {
                case STRING:
                    data = c.getStringCellValue();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(c)) {
                        data = c.getLocalDateTimeCellValue().toLocalDate().toString();
                    } else {
                        data = String.valueOf(c.getNumericCellValue());
                        if (data.endsWith(".0")) {
                            data = data.substring(0, data.length() - 2);
                        }
                    }
                    break;
                case BLANK:
                    data = "";
                    break;
                default:
                    data = "Unsupported cell type";
                    break;
            }

        } catch (Exception e) {
            System.out.println("Excel exception");
            e.printStackTrace();
        }

        return data;
    }
    
    
    public static void WriteExcel(String SheetName, int rNum, int cNum,String data) {
        try {
            FileInputStream fis = new FileInputStream("E:\\Medplus Training\\Java Training\\Sample_Employee_WorkLogs.xlsx");
            Workbook wb = WorkbookFactory.create(fis);
            Sheet s = wb.getSheet(SheetName);
            if (s == null) {
                s = wb.createSheet(SheetName);
            }

            Row r = s.getRow(rNum);
            if (r == null) {
                r = s.createRow(rNum);
            }

            Cell c = r.getCell(cNum);
            if (c == null) {
                c = r.createCell(cNum);
            }

            c.setCellValue(data);
//            fis.close(); 

            FileOutputStream fos = new FileOutputStream("E:\\Medplus Training\\Java Training\\Sample_Employee_WorkLogs.xlsx");
            wb.write(fos);
//            fos.close();
//            wb.close();

        } catch (Exception e) {
            System.out.println("Excel write exception");
            e.printStackTrace();
        }
    }

    
//    public static void WriteExcel(String SheetName, int rNum, int cNum,String data) {
//
//	    try {
//	        FileInputStream fis = new FileInputStream("E:\\Medplus Training\\Java Training\\UserData.xlsx");
//	        Workbook wb = WorkbookFactory.create(fis);
//	        Sheet s = wb.getSheet(SheetName);
//	        if (s == null) {
//	            System.out.println("Sheet not found: " + SheetName);
//	            return;
//	        }
//
//	        Row r = s.getRow(rNum);
//	        if (r == null) {
//	            System.out.println("Row not found: " + rNum);
//	            return;
//	        }
//
//	        Cell c = r.createCell(cNum);
//	        if (c == null) {
//	            System.out.println("Cell not found: " + cNum);
//	            return;
//	        }
//	        c.setCellValue(data);
//	        
//	        FileOutputStream fos=new FileOutputStream("E:\\Medplus Training\\Java Training\\UserData.xlsx");
//	        wb.write(fos);
//
//	    } catch (Exception e) {
//	        System.out.println("Excel write exception");
//	        e.printStackTrace();
//	    }
//	}

    
    
}
