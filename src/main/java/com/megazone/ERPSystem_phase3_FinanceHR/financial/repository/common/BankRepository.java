package com.megazone.ERPSystem_phase3_FinanceHR.financial.repository.common;

import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.common.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
}