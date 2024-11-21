package com.megazone.ERPSystem_phase3_FinanceHR.financial.service.ledger;

import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.ledger.dto.CashJournalSearchDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.ledger.dto.CashJournalShowAllListDTO;

public interface CashJournalService {
    CashJournalShowAllListDTO showAll(CashJournalSearchDTO dto);
}
