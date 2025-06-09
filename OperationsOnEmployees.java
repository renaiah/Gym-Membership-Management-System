package OperatinsOnEmployee;

import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OperationsOnEmployees {
	
	public Map<String, Map<String, Double>> employeeAndMonthAverageWeeklyHours(List<EmployeeWorkLog> employees) {
      System.out.println("\t\tGroup by employee and month; compute average weekly hours.");
      System.out.println("--------------------------------------------------------------------------");
	    Map<String, Map<String, Double>> empMonthHours = new HashMap<>();

	    for (EmployeeWorkLog e : employees) {
	        String empId = e.getEmployeeId();
	        String month = e.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM"));

	        empMonthHours.putIfAbsent(empId, new HashMap<>());
	        Map<String, Double> monthMap = empMonthHours.get(empId);
	        double hours = e.getHoursWorked();
	        monthMap.put(month, monthMap.getOrDefault(month, 0.0) + hours);
	    }
	    
//	    for console 
      for (String empId : empMonthHours.keySet()) {
      for (String month : empMonthHours.get(empId).keySet()) {
          double totalHours = empMonthHours.get(empId).get(month);
          double avgWeekly = totalHours / 4.0;
          System.out.printf("%s - %s : Avg Weekly Hours = %.2f%n", empId, month, avgWeekly);
      	}
      }

	    return empMonthHours;
	}

	public List<EmployeeWorkLog> heighestHoursWorkedEmpsGivenDays(List<EmployeeWorkLog> employees) {
      System.out.println("\t\tTop 5 employees with highest hours in last 60 days");
      System.out.println("--------------------------------------------------------------------------");
      
      LocalDate sixtyDaysAgo = LocalDate.now().minusDays(60);
      
	    List<EmployeeWorkLog> filteredDataEmployees = employees.stream()
	        .filter(e -> !e.getDate().isBefore(sixtyDaysAgo))
	        .sorted(Comparator.comparingDouble(EmployeeWorkLog::getHoursWorked).reversed())
	        .limit(5)
	        .collect(Collectors.toList());
	    
//	    for console 
	    filteredDataEmployees.stream()
	    	.forEach(System.out::println);
	    
	    return filteredDataEmployees;
	}

	public List<List<String>> criticalProjectsTimeAndCount(List<EmployeeWorkLog> employees) {
	    List<List<String>> result = new ArrayList<>();

	    Map<String, Set<String>> projectEmployees = new HashMap<>();
	    Map<String, Double> projectHours = new HashMap<>();

	    for (EmployeeWorkLog e : employees) {
	        String projId = e.getProjectId();
	        String empId = e.getEmployeeId();
	        double hours = e.getHoursWorked();

	        projectEmployees.putIfAbsent(projId, new HashSet<>());
	        projectEmployees.get(projId).add(empId);

	        projectHours.put(projId, projectHours.getOrDefault(projId, 0.0) + hours);
	    }

	    for (String project : projectEmployees.keySet()) {
	        int empCount = projectEmployees.get(project).size();
	        double totalHours = projectHours.get(project);
	        if (empCount > 2 && totalHours >= 10) {
	            result.add(Arrays.asList(project, String.valueOf(empCount), String.format("%.2f", totalHours)));
	        }
	    }

	    if (result.isEmpty()) {
	        result.add(Arrays.asList("No project meets the criteria", "", ""));
	    }

	    return result;
	}

//	public List<List<String>> criticalProjectsTimeAndCount(List<EmployeeWorkLog> employees) {
//		
//      System.out.println("\t\tCritical projects: greater than 5 employees and 20+ hours");
//      System.out.println("--------------------------------------------------------------------------");
//		
//      	List<List<String>> result = new ArrayList<>();
//
//	    Map<String, Set<String>> projectEmployees = new HashMap<>();
//	    Map<String, Double> projectHours = new HashMap<>();
//
//	    for (EmployeeWorkLog emp : employees) {
//	        String projId = emp.getProjectId();
//	        String empId = emp.getEmployeeId();
//	        double hours = emp.getHoursWorked();
//
//	        projectEmployees.putIfAbsent(projId, new HashSet<>());
//	        projectEmployees.get(projId).add(empId);
//
//	        projectHours.put(projId, projectHours.getOrDefault(projId, 0.0) + hours);
//	    }
//
//	    for (String project : projectEmployees.keySet()) {
//	        int empCount = projectEmployees.get(project).size();
//	        double totalHours = projectHours.get(project);
//	        if (empCount > 2 && totalHours >= 20) {
//	        	List<String> projectRow = new ArrayList<>();
//	        	projectRow.add(project); 
//	            projectRow.add(String.valueOf(empCount)); 
//	            projectRow.add(String.format("%.2f", totalHours)); 
//	            result.add(projectRow);
//              System.out.printf("Project : %s,  Employees :  %s,  Total Hours : %.2f%n", project, empCount, totalHours);
//	        }
//	    }
//
//	    if (result.isEmpty()) {
//	        result.add(Arrays.asList("There is none of the project available in given category"));
//          System.out.println("There is none of the project available in given category");
//
//	    }
//
//	    return result;
//	}
	
	public List<String> sortByDeptProjectDateCategory(List<EmployeeWorkLog> employees) {
		
    	System.out.println("		sort by department->ProjectId->Date ");
		System.out.println("--------------------------------------------------------------------------");
		
	    employees.sort(Comparator
	        .comparing(EmployeeWorkLog::getDepartment)
	        .thenComparing(EmployeeWorkLog::getProjectId)
	        .thenComparing(EmployeeWorkLog::getDate));

	    List<String> result = new ArrayList<>();
	    for (EmployeeWorkLog emp : employees) {
	        result.add(emp.toString());
	    }
	    
//	    for console
	    for (String emp : result) {
    	  	System.out.println(emp);
	    }
	    return result;
	}



//    public void employeeAndMonthAverageWeeklyHours(List<EmployeeWorkLog> employees) {
//        System.out.println("\t\tGroup by employee and month; compute average weekly hours.");
//        System.out.println("--------------------------------------------------------------------------");
//
//        Map<String, Map<String, Double>> empMonthHours = new HashMap<>();
//
//        for (EmployeeWorkLog e : employees) {
//            String empId = e.getEmployeeId();
//            String month = e.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM"));
//
//            empMonthHours.putIfAbsent(empId, new HashMap<>());
//            Map<String, Double> monthMap = empMonthHours.get(empId);
//            double hours = e.getHoursWorked();
//            monthMap.put(month, monthMap.getOrDefault(month, 0.0) + hours);
//        }
//
//        for (String empId : empMonthHours.keySet()) {
//            for (String month : empMonthHours.get(empId).keySet()) {
//                double totalHours = empMonthHours.get(empId).get(month);
//                double avgWeekly = totalHours / 4.0;
//                System.out.printf("%s - %s : Avg Weekly Hours = %.2f%n", empId, month, avgWeekly);
//            }
//        }
//    }

//    public void heighestHoursWorkedEmpsGivenDays(List<EmployeeWorkLog> employees) {
//        System.out.println("\t\tTop 5 employees with highest hours in last 60 days");
//        System.out.println("--------------------------------------------------------------------------");
//        
//        LocalDate sixtyDaysAgo = LocalDate.now().minusDays(60);
//    
//        List<EmployeeWorkLog> filteredOnDateEmployees = employees.stream()
//            .filter(e -> !e.getDate().isBefore(sixtyDaysAgo))
//            .collect(Collectors.toList());
//
//        
//        filteredOnDateEmployees.stream()
//            .sorted(Comparator.comparingDouble(e -> -e.getHoursWorked()))
//            .limit(5)
//            .forEach(System.out::println);
//    }

//    public void criticalProjectsTimeAndCount(List<EmployeeWorkLog> employees) {
//        System.out.println("\t\tCritical projects: greater than 5 employees and 20+ hours");
//        System.out.println("--------------------------------------------------------------------------");
//
//        Map<String, Set<String>> projectEmployees = new HashMap<>();
//        Map<String, Double> projectHours = new HashMap<>();
//
//        for (EmployeeWorkLog e : employees) {
//            String projId = e.getProjectId();
//            String empId = e.getEmployeeId();
//            double hours = e.getHoursWorked();
//
//            projectEmployees.putIfAbsent(projId, new HashSet<>());
//            projectEmployees.get(projId).add(empId);
//
//            projectHours.put(projId, projectHours.getOrDefault(projId, 0.0) + hours);
//        }
//
//        boolean isNoAnswer = true;
//        for (String project : projectEmployees.keySet()) {
//            int empCount = projectEmployees.get(project).size();
//            double totalHours = projectHours.get(project);
//            if (empCount > 2 && totalHours >= 10) {
//                System.out.printf("Project : %s,  Employees :  %s,  Total Hours : %.2f%n", project, empCount, totalHours);
//                isNoAnswer = false;
//            }
//        }
//
//        if (isNoAnswer) {
//            System.out.println("There is none of the project available in given category");
//        }
//    }
//
//    public void sortByDeptProjectDateCategory(List<EmployeeWorkLog> employees) {
//        System.out.println("\t\tsort by department->ProjectId->Date");
//        System.out.println("--------------------------------------------------------------------------");
//
//        employees.sort(Comparator
//            .comparing(EmployeeWorkLog::getDepartment)
//            .thenComparing(EmployeeWorkLog::getProjectId)
//            .thenComparing(EmployeeWorkLog::getDate));
//
//        for (EmployeeWorkLog e : employees) {
//            System.out.println(e);
//        }
//    }
}




//package OperatinsOnEmployee;
//
//import java.util.*;
//import java.util.Comparator;
//import java.util.List;
//
//public class OperationsOnEmployees {
//	
//	public void employeeAndMonthAverageWeeklyHours(List<Employee> employees) {
//		
//		System.out.println("		Group by employee and month; compute average weekly hours.");
//		System.out.println("--------------------------------------------------------------------------");
//	    Map<String, Map<String, Double>> empMonthHours = new HashMap<>();
//
//	    for (Employee e : employees) {
//	        String empId = e.getEmpId();
//	        String month = e.getDate().substring(0, 7); //(YYYY-MM) for key in second map
//
//	        empMonthHours.putIfAbsent(empId, new HashMap<>());
//	        Map<String, Double> monthMap = empMonthHours.get(empId);
//	        double hours = Double.parseDouble(e.getHoursWorked());
//	        monthMap.put(month, monthMap.getOrDefault(month, 0.0) + hours);
//	    }
//
//	    for (String empId : empMonthHours.keySet()) {
//	        for (String month : empMonthHours.get(empId).keySet()) {
//	            double totalHours = empMonthHours.get(empId).get(month);
//	            double avgWeekly = totalHours / 4.0;
////	            System.out.println(empId + " - " + month + " : Avg Weekly Hours = " + avgWeekly);
//	            System.out.printf("%s - %s : Avg Weekly Hours = %.2f%n", empId, month, avgWeekly);
//	        }
//	    }
//	}
//
//
//    public void heighestHoursWorkedEmpsGivenDays(ArrayList<Employee> employees) {
//    	
//    	System.out.println("		Top 5 employees with highest hours in last 60 days");
//		System.out.println("--------------------------------------------------------------------------");
//    	employees.stream()
//        .sorted(Comparator.comparingDouble(e -> -Double.parseDouble(e.getHoursWorked()))) // Descending
//        .limit(5)
//        .forEach(System.out::println);
//
//    }
//    
//    public void criticalProjectsTimeAndCount(List<Employee> employees) {
//    	
//    	System.out.println("		Critical projects: >5 employees and 200+ hours");
//		System.out.println("--------------------------------------------------------------------------");
//        Map<String, Set<String>> projectEmployees = new HashMap<>();
//        Map<String, Double> projectHours = new HashMap<>();
//
//        for (Employee e : employees) {
//            String projId = e.getProjectId();
//            String empId = e.getEmpId();
//            String hoursStr = e.getHoursWorked();
//            double hours = hoursStr.isEmpty() ? 0.0 : Double.parseDouble(hoursStr);
//
//            projectEmployees.putIfAbsent(projId, new HashSet<>());
//            projectEmployees.get(projId).add(empId);
//
//            projectHours.put(projId, projectHours.getOrDefault(projId, 0.0) + hours);
//        }
//        boolean isNoAnswer=true;
//        for (String project : projectEmployees.keySet()) {
//            int empCount = projectEmployees.get(project).size();
//            double totalHours = projectHours.get(project);
//            if (empCount > 2 && totalHours >= 100) {
////                System.out.println("Project: " + proj + ", Employees: " + empCount + ", Total Hours: " + totalHours);
//                System.out.printf("Project : %s,  Employees :  %s,  Total Hours : %.2f%n", project, empCount, totalHours);
//                isNoAnswer=false;
//            }
//        }
//        if(isNoAnswer) {
//        	System.out.println("There is none of the projrct available given category");
//        }
//    }
//
//    
//    public void sortByDeptProjectDateCategory(ArrayList<Employee> employees) {
//    	System.out.println("		sort by department->ProjectId->Date ");
//		System.out.println("--------------------------------------------------------------------------");
//        employees.sort(Comparator
//            .comparing(Employee::getDepartment)
//            .thenComparing(Employee::getProjectId)
//            .thenComparing(Employee::getDate));
//
//        for (Employee e : employees) {
//            System.out.println(e);
//        }
//    }
//
//}
