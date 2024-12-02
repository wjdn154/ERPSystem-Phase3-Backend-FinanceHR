package com.megazone.ERPSystem_phase3_FinanceHR.financial.service.financial_statements_ledger;

import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.financial_statements.dto.IncomeStatementLedgerDashBoardDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.financial_statements.dto.IncomeStatementLedgerShowDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.financial_statements.dto.IncomeStatementSearchDTO;

import java.util.List;

public interface IncomeStatementService {
    List<IncomeStatementLedgerShowDTO> show(IncomeStatementSearchDTO dto);
    IncomeStatementLedgerDashBoardDTO dashBoardShow();
}
