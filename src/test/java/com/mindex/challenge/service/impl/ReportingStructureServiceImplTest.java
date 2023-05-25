package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.ReportingStructure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {
    private static final Object TOP_EMPLOYEE_ID = "16a596ae-edd3-4847-99fe-c4518e82c86f";

    private String reportStructureUrl;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        reportStructureUrl = "http://localhost:" + port + "/numberOfReports/{id}";
    }

    @Test
    public void testTopEmployeeList() {
        ReportingStructure reportingStructure = restTemplate.getForEntity(reportStructureUrl, ReportingStructure.class, TOP_EMPLOYEE_ID).getBody();
        assertNotNull(reportingStructure);
        assertEquals(TOP_EMPLOYEE_ID, reportingStructure.getEmployee().getEmployeeId());
        assertEquals(4, reportingStructure.getNumberOfReports());
    }
}