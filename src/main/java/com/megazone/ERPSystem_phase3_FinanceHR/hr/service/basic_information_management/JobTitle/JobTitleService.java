package com.megazone.ERPSystem_phase3_FinanceHR.hr.service.basic_information_management.JobTitle;

import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.JobTitle;

import java.util.Optional;

public interface JobTitleService {
    Optional<JobTitle> getJobTitleById(Long id);
}
