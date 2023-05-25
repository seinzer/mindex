package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    CompensationRepository compensationRepository;

    @Override
    public Compensation read(String employeeId) {
        return compensationRepository.findByCompensationId(employeeId);
    }

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating Compensation [{}]", compensation);

        compensation.setCompensationId(compensation.getEmployee().getEmployeeId());
        return compensationRepository.insert(compensation);
    }
}
