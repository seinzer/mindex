package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServicleImplTest {
    private static final String TOP_EMPLOYEE_ID = "16a596ae-edd3-4847-99fe-c4518e82c86f";
    private static final String PETE_BEST_ID = "62c1084e-6e34-4630-93fd-9153afb65309";

    private String compensationUrl;
    private String compensationCreateUrl;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation/{id}";
        compensationCreateUrl = "http://localhost:" + port + "/compensation";
    }

    @Test
    public void testReadCompensation() throws ParseException {
        Compensation compensation = restTemplate.getForEntity(compensationUrl, Compensation.class, TOP_EMPLOYEE_ID).getBody();
        assertNotNull(compensation);
        assertNotNull(compensation.getEmployee());
        assertEquals(100000.0, compensation.getSalary(), 0.001);
        Date expectedDate = new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-01");
        assertEquals(expectedDate, compensation.getEffectiveDate());
    }

    @Test
    public void testReadEmptyCompensation() throws ParseException {
        Compensation compensation = restTemplate.getForEntity(compensationUrl, Compensation.class, "").getBody();
        assertNotNull(compensation);
        assertNull(compensation.getEmployee());
        assertEquals(0.0, compensation.getSalary(), 0.001);
        assertNull(compensation.getEffectiveDate());
    }

    @Test
    public void testCreateCompensation() throws ParseException {
        Compensation testCompensation = new Compensation();
        testCompensation.setEmployee(new Employee());
        testCompensation.getEmployee().setEmployeeId(PETE_BEST_ID);
        testCompensation.setSalary(10000.00);
        testCompensation.setEffectiveDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-03-03"));
        Compensation createdCompensation = restTemplate.postForEntity(compensationCreateUrl, testCompensation, Compensation.class).getBody();
        assertNotNull(createdCompensation);
        assertEquals(PETE_BEST_ID, createdCompensation.getCompensationId());
        assertEquals(PETE_BEST_ID, createdCompensation.getEmployee().getEmployeeId());
        assertEquals(testCompensation.getSalary(), createdCompensation.getSalary(), 0.001);
        assertEquals(testCompensation.getEffectiveDate(), createdCompensation.getEffectiveDate());
    }
}
