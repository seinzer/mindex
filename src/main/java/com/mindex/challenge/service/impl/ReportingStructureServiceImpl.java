package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
    @Autowired
    EmployeeService employeeService;

    @Override
    public ReportingStructure read(String id) {
        ReportingStructure reportingStructure = new ReportingStructure();
        Employee employee = employeeService.read(id);

        reportingStructure.setEmployee(employee);
        reportingStructure.setNumberOfReports(getReportsCount(employee));
        return reportingStructure;
    }

    private int getReportsCount(Employee employee) {
        int count = 0;
        if (employee.getDirectReports() != null) {
            count = employee.getDirectReports().size();
            for (Employee directReport : employee.getDirectReports()) {
                directReport = employeeService.read(directReport.getEmployeeId());
                count += getReportsCount(directReport);
            }
        }
        return count;
    }
}
