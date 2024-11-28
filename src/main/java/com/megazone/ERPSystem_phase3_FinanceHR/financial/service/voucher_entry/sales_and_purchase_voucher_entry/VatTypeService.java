package com.megazone.ERPSystem_phase3_FinanceHR.financial.service.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatAmountWithQuantityPriceDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatAmountWithSupplyAmountDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatTypeShowDTO;

import java.math.BigDecimal;
import java.util.List;

public interface VatTypeService {
    BigDecimal vatAmountCalculate(VatAmountWithQuantityPriceDTO dto);
    BigDecimal vatAmountCalculate(VatAmountWithSupplyAmountDTO dto);

    Long vatTypeGetId(String vatTypeId);
//    List<VatTypeShowDTO> vatTypeGetAll(List<Long> vatTypeIdList);
}
