package com.megazone.ERPSystem_phase3_FinanceHR.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.vatType;

import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.VatType;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatTypeShowDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface VatTypeRepository extends JpaRepository<VatType, Long>,VatTypeRepositoryCustom {
    @Query("SELECT v FROM VatType v JOIN FETCH v.accountSubject WHERE v.code = :code")
    VatType findByCode(@Param("code")String code);
    List<VatType> findAllByTransactionType(TransactionType transactionType);
}
