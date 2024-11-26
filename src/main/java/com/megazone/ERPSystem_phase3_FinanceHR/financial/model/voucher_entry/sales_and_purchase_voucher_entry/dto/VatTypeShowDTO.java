package com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto;

import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.VatType;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VatTypeShowDTO {
    private Long vatTypeId;
    private String vatTypeCode;
    private String vatTypeName;
    private String description;
    private TransactionType transactionType;

    public static VatTypeShowDTO create(VatType vatType) {
        return new VatTypeShowDTO(
                vatType.getId(),
                vatType.getCode(),
                vatType.getVatName(),
                vatType.getDescription(),
                vatType.getTransactionType()
        );
    }
}
