package com.megazone.ERPSystem_phase3_FinanceHR.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.vatType;

import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatTypeShowDTO;

import java.util.List;

public interface VatTypeRepositoryCustom {
    List<VatTypeShowDTO> getVatTypeAll(List<Long> vatTypeIdList);
}
