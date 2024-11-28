package com.megazone.ERPSystem_phase3_FinanceHR.financial.kafka_listener;

import com.megazone.ERPSystem_phase3_FinanceHR.financial.service.voucher_entry.sales_and_purchase_voucher_entry.VatTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class VatTypeKafkaListener {
    private final VatTypeService vatTypeService;

    @KafkaListener(topics = "vat-type-request-topic", groupId = "vat-type-service-group")
    public void vatTypeGetListener(Map<String, Object> request) {
        vatTypeService.vatTypeGet(request);
    }

    @KafkaListener(topics = "vat-type-list-request-topic", groupId = "vat-type-service-group")
    public void vatTypeGetAllListener(Map<String, Object> request) {
        vatTypeService.vatTypeGetAll(request);
    }
}
