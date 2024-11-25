package com.megazone.ERPSystem_phase3_FinanceHR.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.vatType;

import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.QVatType;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatTypeShowDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.TransactionType;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VatTypeRepositoryImpl implements VatTypeRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    private String vatTypeCode;
    private String vatTypeName;
    private String description;
    private TransactionType transactionType;

    @Override
    public List<VatTypeShowDTO> getVatTypeAll(List<Long> vatTypeIdList) {
        QVatType vatType = QVatType.vatType;
        return jpaQueryFactory
                .select(
                        Projections.constructor(VatTypeShowDTO.class,
                                vatType.id,
                                vatType.code,
                                vatType.vatName,
                                vatType.description,
                                vatType.transactionType)
                )
                .from(vatType)
                .where(vatType.id.in(vatTypeIdList))
                .fetch();

    }
}
