package EmployeeProductivityAndAnalyticsSystem;

import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OperationsOnEmployees {
	
//	public Map<String, Map<String, Double>> employeeAverageWeeklyHours(List<EmployeeWorkLog> employees) {
//      System.out.println("\t\tGroup by employee and month, average of weekly hours.");
//      System.out.println("--------------------------------------------------------------------------");
//	    Map<String, Map<String, Double>> empMonthHours = new HashMap<>();
//
//	    for (EmployeeWorkLog emp : employees) {
//	        String empId = emp.getEmployeeId();
//	        String month = emp.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM"));
//
//	        empMonthHours.putIfAbsent(empId, new HashMap<>());
//	        Map<String, Double> monthMap = empMonthHours.get(empId);
//	        double hours = emp.getHoursWorked();
//	        monthMap.put(month, monthMap.getOrDefault(month, 0.0) + hours);
//	    }
//	    
////	    for console 
//      for (String empId : empMonthHours.keySet()) {
//      for (String month : empMonthHours.get(empId).keySet()) {
//          double totalHours = empMonthHours.get(empId).get(month);
//          double avgWeekly = totalHours / 4.0;
//          System.out.printf("%s - %s : Avg Weekly Hours = %.2f%n", empId, month, avgWeekly);
//      	}
//      }
//	    return empMonthHours;
//	}

	public Map<String, Map<String, Double>> employeeAverageWeeklyHours(List<EmployeeWorkLog> employees) {
	    System.out.println("\t\tGroup by employee and month, average of weekly hours.");
	    System.out.println("--------------------------------------------------------------------------");

	    Map<String, Map<String, Double>> result = employees.stream()
	        .collect(Collectors.groupingBy(
	            EmployeeWorkLog::getEmployeeId,
	            Collectors.groupingBy(
	                e -> e.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM")),
	                Collectors.summingDouble(EmployeeWorkLog::getHoursWorked)
	            )
	        ));

	    // for console 
	    result.forEach((empId, monthMap) -> {
	        monthMap.forEach((month, totalHours) -> {
	            double avgWeekly = totalHours / 4.0;
	            System.out.printf("%s - %s : Avg Weekly Hours = %.2f%n", empId, month, avgWeekly);
	        });
	    });

	    return result;
	}

	
	public List<EmployeeWorkLog> heighestHoursWorkedEmps(List<EmployeeWorkLog> employees) {
      System.out.println("\t\tTop 5 employees with highest hours in last 60 days");
      System.out.println("--------------------------------------------------------------------------");
      
      LocalDate sixtyDaysAgo = LocalDate.now().minusDays(60);
      
	    List<EmployeeWorkLog> filteredEmployees = employees.stream()
	        .filter(e -> !e.getDate().isBefore(sixtyDaysAgo))
	        .sorted(Comparator.comparingDouble(EmployeeWorkLog::getHoursWorked).reversed())
	        .limit(5)
	        .collect(Collectors.toList());
	    
//	    for console 
	    filteredEmployees.stream()
	    	.forEach(System.out::println);
	    
	    return filteredEmployees;
	}

//	public List<List<String>> criticalProjects(List<EmployeeWorkLog> employees) {
//		
//      System.out.println("\t\tCritical projects: greater than 2 employees and 20+ hours");
//      System.out.println("--------------------------------------------------------------------------");
//	    List<List<String>> result = new ArrayList<>();
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
//	        projectHours.put(projId, projectHours.getOrDefault(projId, 0.0) + hours);
//	    }
//
//	    for (String project : projectEmployees.keySet()) {
//	        int empCount = projectEmployees.get(project).size();
//	        double totalHours = projectHours.get(project);
//	        if (empCount > 2 && totalHours >= 20) {
//	            result.add(Arrays.asList(project, String.valueOf(empCount), String.format("%.2f", totalHours)));
//              System.out.printf("Project : %s,  Employees :  %s,  Total Hours : %.2f%n", project, empCount, totalHours);
//	        }
//	    }
//
//	    if (result.isEmpty()) {
//	        result.add(Arrays.asList("There is none of the project available in given criteria", "", ""));
//          System.out.println("There is none of the project available in given category");
//	    }
//
//	    return result;
//	}

	
	public List<List<String>> criticalProjects(List<EmployeeWorkLog> employees) {
	    System.out.println("\t\tCritical projects: greater than 2 employees and 20+ hours");
	    System.out.println("--------------------------------------------------------------------------");

	    Map<String, Set<String>> projectToEmployees = employees.stream()
	        .collect(Collectors.groupingBy(
	            EmployeeWorkLog::getProjectId,
	            Collectors.mapping(EmployeeWorkLog::getEmployeeId, Collectors.toSet())
	        ));

	    Map<String, Double> projectToHours = employees.stream()
	        .collect(Collectors.groupingBy(
	            EmployeeWorkLog::getProjectId,
	            Collectors.summingDouble(EmployeeWorkLog::getHoursWorked)
	        ));

	    List<List<String>> result = projectToEmployees.entrySet().stream()
	        .filter(e -> e.getValue().size() > 2 && projectToHours.getOrDefault(e.getKey(), 0.0) >= 20.0)
	        .map(e -> {
	            String project = e.getKey();
	            int empCount = e.getValue().size();
	            double totalHours = projectToHours.get(project);
	            System.out.printf("Project : %s,  Employees :  %s,  Total Hours : %.2f%n", project, empCount, totalHours);
	            return Arrays.asList(project, String.valueOf(empCount), String.format("%.2f", totalHours));
	        })
	        .collect(Collectors.toList());

	    if (result.isEmpty()) {
	        result.add(Arrays.asList("There is none of the project available in given criteria", "", ""));
	        System.out.println("There is none of the project available in given category");
	    }

	    return result;
	}

	
	public List<String> sortByDeptProjectDate(List<EmployeeWorkLog> employees) {
		
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
	    result.stream()
	    .forEach(System.out::println);
	    
	    return result;
	}

}
