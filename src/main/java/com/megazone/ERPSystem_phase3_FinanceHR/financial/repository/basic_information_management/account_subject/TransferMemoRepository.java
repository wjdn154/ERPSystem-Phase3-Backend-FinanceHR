package com.megazone.ERPSystem_phase3_FinanceHR.financial.repository.basic_information_management.account_subject;

import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.basic_information_management.account_subject.TransferMemo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferMemoRepository extends JpaRepository<TransferMemo, Long> {
}