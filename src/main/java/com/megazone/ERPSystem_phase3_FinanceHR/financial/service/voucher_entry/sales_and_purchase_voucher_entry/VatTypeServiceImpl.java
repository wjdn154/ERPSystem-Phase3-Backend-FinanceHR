package com.megazone.ERPSystem_phase3_FinanceHR.financial.service.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatAmountWithQuantityPriceDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatAmountWithSupplyAmountDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatTypeShowDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.vatType.VatTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VatTypeServiceImpl implements VatTypeService {
    private final VatTypeRepository vatTypeRepository;


    @Override
    public BigDecimal vatAmountCalculate(VatAmountWithQuantityPriceDTO dto) {
        return dto.getQuantity().multiply(dto.getPrice()).multiply(
                vatTypeRepository.findById(dto.getVatTypeId()).orElseThrow(
                        () -> new RuntimeException("해당하는 부가세 유형이 없습니다.")).getTaxRate());
    }

    @Override
    public BigDecimal vatAmountCalculate(VatAmountWithSupplyAmountDTO dto) {
        return dto.getSupplyAmount().multiply(
                vatTypeRepository.findById(dto.getVatTypeId()).orElseThrow(
                        () -> new RuntimeException("해당하는 부가세 유형이 없습니다.")).getTaxRate());
    }

    @Override
    public Long vatTypeGetId(String vatTypeCode) {
        return vatTypeRepository.findByCode(vatTypeCode).getId();
    }

    @Override
    public VatTypeShowDTO vatTypeGet(Long vatTypeId) {
        return VatTypeShowDTO.create(vatTypeRepository.findById(vatTypeId).orElseThrow(
                () -> new RuntimeException("해당하는 부가세 유형이 없습니다.")));
    }

    @Override
    public List<VatTypeShowDTO> vatTypeGetAll(List<Long> vatTypeIdList) {
        List<VatTypeShowDTO> vatTypeShowDTOList = vatTypeRepository.getVatTypeAll(vatTypeIdList);
        return vatTypeShowDTOList;
    }
}
