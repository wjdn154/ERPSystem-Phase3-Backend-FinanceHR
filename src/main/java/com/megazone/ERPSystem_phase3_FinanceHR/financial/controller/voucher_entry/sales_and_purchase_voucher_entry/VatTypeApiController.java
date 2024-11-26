package com.megazone.ERPSystem_phase3_FinanceHR.financial.controller.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatAmountWithQuantityPriceDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatAmountWithSupplyAmountDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatTypeShowAllDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatTypeShowDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.TransactionType;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.vatType.VatTypeRepository;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.service.voucher_entry.sales_and_purchase_voucher_entry.VatTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class VatTypeApiController {

    private final VatTypeRepository vatTypeRepository;
    private final VatTypeService vatTypeService;


    @PostMapping("/api/financial/vatType/show")
    public ResponseEntity<VatTypeShowAllDTO> vatTypeShow() {
        VatTypeShowAllDTO vatTypeShowAllDTO = new VatTypeShowAllDTO();
        List<VatTypeShowDTO> salesVatType = vatTypeRepository.findAllByTransactionType(TransactionType.SALES)
                .stream().map(VatTypeShowDTO::create).toList();
        List<VatTypeShowDTO> purchaseVatType = vatTypeRepository.findAllByTransactionType(TransactionType.PURCHASE)
                .stream().map(VatTypeShowDTO::create).toList();
        vatTypeShowAllDTO.setSalesVatTypeShowDTO(salesVatType);
        vatTypeShowAllDTO.setPurchaseVatTypeShowDTO(purchaseVatType);

        if(vatTypeShowAllDTO == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(vatTypeShowAllDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(vatTypeShowAllDTO);
    }
    // 수량, 단가, 부가세유형 코드 = 부가세 금액
    @PostMapping("/api/financial/vatType/vatAmount/quantityPrice")
    public ResponseEntity<Object> vatAmountWithQuantityPrice(@RequestBody VatAmountWithQuantityPriceDTO dto) {

        try {
            BigDecimal vatAmount = vatTypeService.vatAmountCalculate(dto);
            return ResponseEntity.status(HttpStatus.OK).body(vatAmount);
        }
        catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // 공급가액, 부가세유형 코드 = 부가세 금액
    @PostMapping("/api/financial/vatType/vatAmount/supplyAmount")
    public ResponseEntity<Object> vatAmountSupplyAmount(@RequestBody VatAmountWithSupplyAmountDTO dto) {
        try {
            BigDecimal vatAmount = vatTypeService.vatAmountCalculate(dto);
            return ResponseEntity.status(HttpStatus.OK).body(vatAmount);
        }
        catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
    @PostMapping("/api/financial/vatType/vatType/id")
    public ResponseEntity<Object> vatTypeGetId(@RequestBody Map<String, String> vatTypeCode) {
        try {
            String targetCode = vatTypeCode.get("vatTypeCode");
            Long vatTypeId = vatTypeService.vatTypeGetId(targetCode);
            return ResponseEntity.status(HttpStatus.OK).body(vatTypeId);
        }
        catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PostMapping("/api/financial/vatType/vatType/get")
    public ResponseEntity<Object> vatTypeGet(@RequestBody Map<String, Long> vatTypeId) {
        try {
            Long targetId = vatTypeId.get("vatTypeId");
            VatTypeShowDTO vatType = vatTypeService.vatTypeGet(targetId);
            return ResponseEntity.status(HttpStatus.OK).body(vatType);
        }
        catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PostMapping("api/financial/vatType/vatType/getAll")
    public ResponseEntity<Object> vatTypeGetAll(@RequestBody List<Long> vatTypeIdList) {
        try {
            List<VatTypeShowDTO> vatTypeList = vatTypeService.vatTypeGetAll(vatTypeIdList);
            return ResponseEntity.status(HttpStatus.OK).body(vatTypeList);
        }
        catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}