package com.megazone.ERPSystem_phase3_FinanceHR.financial.service.ledger;

import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.ledger.dto.ClientLedgerSearchDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.ledger.dto.ClientLedgerShowAllDTO;

public interface ClientLedgerService {
    ClientLedgerShowAllDTO show(ClientLedgerSearchDTO dto);
}
