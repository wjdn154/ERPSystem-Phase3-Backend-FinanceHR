package com.megazone.ERPSystem_phase3_FinanceHR.hr.service.salary_ledger;

import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.salary_ledger.dto.SalaryLedgerDateShowDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.basic_information_management.salary_ledger.SalaryLedgerDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SalaryLedgerDateServiceImpl implements SalaryLedgerDateService {
    private final SalaryLedgerDateRepository salaryLedgerDateRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SalaryLedgerDateShowDTO> showAll() {
        return salaryLedgerDateRepository.findAll().stream().map(
                (result) -> SalaryLedgerDateShowDTO.create(result)
        ).toList();
    }
}
