package com.megazone.ERPSystem_phase3_FinanceHR.hr.service.basic_information_management.JobTitle;

import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.JobTitle;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.basic_information_management.JobTitle.JobTitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class JobTitleServiceImpl implements JobTitleService {
    private final JobTitleRepository jobTitleRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<JobTitle> getJobTitleById(Long id) {
        return jobTitleRepository.findById(id);
    }
}
