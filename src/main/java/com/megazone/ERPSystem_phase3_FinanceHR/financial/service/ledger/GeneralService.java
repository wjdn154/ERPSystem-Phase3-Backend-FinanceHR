package com.megazone.ERPSystem_phase3_FinanceHR.financial.service.ledger;

import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.ledger.dto.*;

import java.util.List;

public interface GeneralService {
    List<GeneralAccountListDTO> getGeneralShow(GeneralDTO dto);

    GeneralShowAllDTO getGeneralSelectShow(GeneralSelectDTO dto);
}
