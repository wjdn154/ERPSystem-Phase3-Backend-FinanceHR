package com.megazone.ERPSystem_phase3_FinanceHR.financial.service.ledger;

import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.ledger.dto.SalesAndPurChaseLedgerSearchDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.ledger.dto.SalesAndPurChaseLedgerShowAllDTO;

public interface SalesAndPurchaseLedgerService {
    SalesAndPurChaseLedgerShowAllDTO showAll(SalesAndPurChaseLedgerSearchDTO dto);
}
