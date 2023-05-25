package com.mindex.challenge;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataBootstrapTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompensationRepository compensationRepository;

    @Test
    public void testEmployee() {
        Employee employee = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
        assertNotNull(employee);
        assertEquals("John", employee.getFirstName());
        assertEquals("Lennon", employee.getLastName());
        assertEquals("Development Manager", employee.getPosition());
        assertEquals("Engineering", employee.getDepartment());
    }

    @Test
    public void testCompensation() throws ParseException {
        Compensation compensation = compensationRepository.findByCompensationId("16a596ae-edd3-4847-99fe-c4518e82c86f");
        assertNotNull(compensation);
        assertNotNull(compensation.getEmployee());
        assertEquals(100000.0, compensation.getSalary(), 0.001);
        Date expectedDate = new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-01");
        assertEquals(expectedDate, compensation.getEffectiveDate());
    }
}