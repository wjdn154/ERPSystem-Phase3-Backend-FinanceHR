package com.megazone.ERPSystem_phase3_FinanceHR.financial.service.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase3_FinanceHR.common.config.multi_tenant.TenantContext;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatAmountWithQuantityPriceDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatAmountWithSupplyAmountDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatTypeShowDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.vatType.VatTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class VatTypeServiceImpl implements VatTypeService {
    private final VatTypeRepository vatTypeRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;


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

    @KafkaListener(topics = "vat-type-request-topic", groupId = "vat-type-service-group")
    public void handleVatTypeGetRequest(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        Long vatTypeId = Long.valueOf((Integer) request.get("vatTypeId"));
        String currentTenant = (String) request.get("currentTenant");

        try {
            TenantContext.setCurrentTenant(currentTenant);

            // 데이터 처리
            VatTypeShowDTO vatTypeShowDTO = VatTypeShowDTO.create(vatTypeRepository.findById(vatTypeId).orElseThrow(
                    () -> new RuntimeException("해당하는 부가세 유형이 없습니다."))); // 단건 조회 로직 호출

            // 응답 메시지 생성 및 Kafka 전송
            kafkaTemplate.send("vat-type-response-topic", requestId, Map.of(
                    "requestId", requestId,
                    "data", vatTypeShowDTO
            )).thenAccept(sendResult -> {
                System.out.println("단건 응답 Kafka 메시지 전송 성공: " + sendResult.getRecordMetadata());
            }).exceptionally(ex -> {
                System.err.println("단건 응답 Kafka 메시지 전송 실패: " + ex.getMessage());
                return null;
            });

        } catch (Exception e) {
            // 예외 발생 시 실패 응답 전송
            kafkaTemplate.send("vat-type-response-topic", requestId, Map.of(
                    "requestId", requestId,
                    "error", e.getMessage()
            )).thenAccept(sendResult -> {
                System.out.println("단건 실패 응답 Kafka 메시지 전송 성공: " + sendResult.getRecordMetadata());
            }).exceptionally(ex -> {
                System.err.println("단건 실패 응답 Kafka 메시지 전송 실패: " + ex.getMessage());
                return null;
            });

        } finally {
            TenantContext.setCurrentTenant("PUBLIC");
        }
    }

    @KafkaListener(topics = "vat-type-list-request-topic", groupId = "vat-type-service-group")
    public void handleVatTypeGetAllRequest(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        List<Long> vatTypeIdList = (List<Long>) request.get("vatTypeIdList");
        String currentTenant = (String) request.get("currentTenant");

        try {
            TenantContext.setCurrentTenant(currentTenant);

            // 데이터 처리
            List<VatTypeShowDTO> vatTypeShowDTOList = vatTypeRepository.getVatTypeAll(vatTypeIdList);

            // 응답 메시지 생성 및 Kafka 전송
            kafkaTemplate.send("vat-type-list-response-topic", requestId, Map.of(
                    "requestId", requestId,
                    "data", vatTypeShowDTOList
            )).thenAccept(sendResult -> {
                System.out.println("응답 Kafka 메시지 전송 성공: " + sendResult.getRecordMetadata());
            }).exceptionally(ex -> {
                System.err.println("응답 Kafka 메시지 전송 실패: " + ex.getMessage());
                return null;
            });

        } catch (Exception e) {
            // 예외 발생 시 실패 응답 전송
            kafkaTemplate.send("vat-type-list-response-topic", requestId, Map.of(
                    "requestId", requestId,
                    "error", e.getMessage()
            )).thenAccept(sendResult -> {
                System.out.println("실패 응답 Kafka 메시지 전송 성공: " + sendResult.getRecordMetadata());
            }).exceptionally(ex -> {
                System.err.println("실패 응답 Kafka 메시지 전송 실패: " + ex.getMessage());
                return null;
            });

        } finally {
            TenantContext.setCurrentTenant("PUBLIC");
        }
    }
}
