package com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.basic_information_management.salary_ledger;

import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.salary_ledger.dto.SalaryLedgerAllowanceShowDTO;

import java.util.List;

public interface SalaryAllowanceRepositoryCustom {
    List<SalaryLedgerAllowanceShowDTO> findSalaryAllowanceList(Long salaryId);
}
