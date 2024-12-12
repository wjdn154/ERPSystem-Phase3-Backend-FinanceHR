package com.megazone.ERPSystem_phase3_FinanceHR.hr.service.salary_ledger;

import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.salary_ledger.dto.SalaryLedgerAllowanceShowDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.basic_information_management.salary_ledger.SalaryAllowanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SalaryAllowanceServiceImpl implements SalaryAllowanceService {
    private final SalaryAllowanceRepository salaryAllowanceRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SalaryLedgerAllowanceShowDTO> findSalaryAllowances(Long salaryId) {
        return salaryAllowanceRepository.findSalaryAllowanceList(salaryId);
    }
}
