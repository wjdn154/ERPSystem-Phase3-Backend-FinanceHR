package com.megazone.ERPSystem_phase3_FinanceHR.financial.service.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase3_FinanceHR.common.config.multi_tenant.TenantContext;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatAmountWithQuantityPriceDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatAmountWithSupplyAmountDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatTypeShowDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.vatType.VatTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.KafkaProducerHelper;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class VatTypeServiceImpl implements VatTypeService {
    private final VatTypeRepository vatTypeRepository;
    private final KafkaProducerHelper kafkaProducerHelper;


    @Override
    @Transactional(readOnly = true)
    public BigDecimal vatAmountCalculate(VatAmountWithQuantityPriceDTO dto) {
        return dto.getQuantity().multiply(dto.getPrice()).multiply(
                vatTypeRepository.findById(dto.getVatTypeId()).orElseThrow(
                        () -> new RuntimeException("해당하는 부가세 유형이 없습니다.")).getTaxRate());
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal vatAmountCalculate(VatAmountWithSupplyAmountDTO dto) {
        return dto.getSupplyAmount().multiply(
                vatTypeRepository.findById(dto.getVatTypeId()).orElseThrow(
                        () -> new RuntimeException("해당하는 부가세 유형이 없습니다.")).getTaxRate());
    }

    @Override
    @Transactional(readOnly = true)
    public Long vatTypeGetId(String vatTypeCode) {
        return vatTypeRepository.findByCode(vatTypeCode).getId();
    }

    @Override
    public void vatTypeGet(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        Long vatTypeId = Long.valueOf((Integer) request.get("vatTypeId"));
        String currentTenant = (String) request.get("currentTenant");

        try {
            TenantContext.setCurrentTenant(currentTenant);

            // 데이터 처리
            VatTypeShowDTO vatTypeShowDTO = VatTypeShowDTO.create(vatTypeRepository.findById(vatTypeId).orElseThrow(
                    () -> new RuntimeException("해당하는 부가세 유형이 없습니다.")));

            // Kafka로 응답 전송
            kafkaProducerHelper.sendMessage("vat-type-response-topic", requestId, Map.of(
                    "requestId", requestId,
                    "data", vatTypeShowDTO
            ));
        } catch (Exception e) {
            // 예외 발생 시 실패 응답 전송
            kafkaProducerHelper.sendMessage("vat-type-response-topic", requestId, Map.of(
                    "requestId", requestId,
                    "error", e.getMessage()
            ));
        } finally {
            TenantContext.setCurrentTenant("PUBLIC");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void vatTypeGetAll(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        List<Long> vatTypeIdList = (List<Long>) request.get("vatTypeIdList");
        String currentTenant = (String) request.get("currentTenant");

        try {
            TenantContext.setCurrentTenant(currentTenant);

            // 데이터 처리
            List<VatTypeShowDTO> vatTypeShowDTOList = vatTypeRepository.getVatTypeAll(vatTypeIdList);

            // Kafka로 응답 전송
            kafkaProducerHelper.sendMessage("vat-type-list-response-topic", requestId, Map.of(
                    "requestId", requestId,
                    "data", vatTypeShowDTOList
            ));
        } catch (Exception e) {
            // 예외 발생 시 실패 응답 전송
            kafkaProducerHelper.sendMessage("vat-type-list-response-topic", requestId, Map.of(
                    "requestId", requestId,
                    "error", e.getMessage()
            ));
        } finally {
            TenantContext.setCurrentTenant("PUBLIC");
        }
    }
}
