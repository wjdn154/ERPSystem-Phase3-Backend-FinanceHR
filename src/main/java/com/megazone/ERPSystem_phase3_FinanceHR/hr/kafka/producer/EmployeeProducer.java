package com.megazone.ERPSystem_phase3_FinanceHR.hr.kafka.producer;

import com.megazone.ERPSystem_phase3_FinanceHR.common.config.multi_tenant.TenantContext;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.KafkaProducerHelper;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatTypeShowDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.dto.EmployeeShowToDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class EmployeeProducer {

    private final KafkaProducerHelper kafkaProducerHelper;
    private final String employeeUpdateTopic = "employee-update";
    private final String employeeSaveTopic = "employee-save";

    public void employeeUpdateProducer(EmployeeShowToDTO dto) {
        String requestId = UUID.randomUUID().toString();
        String currentTenant = TenantContext.getCurrentTenant();

        Map<String, Object> messagePayload = Map.of(
                "requestId", requestId,
                "tenant", currentTenant,
                "data", dto
        );

        try {
            kafkaProducerHelper.sendMessage(employeeUpdateTopic, requestId, messagePayload);
        } catch (Exception e) {
            kafkaProducerHelper.sendMessage(employeeUpdateTopic, requestId, Map.of(
                    "requestId", requestId,
                    "error", e.getMessage()
            ));
        }
    }

    public void employeeSaveProducer(EmployeeShowToDTO dto) {
        String requestId = UUID.randomUUID().toString();
        String currentTenant = TenantContext.getCurrentTenant();

        Map<String, Object> messagePayload = Map.of(
                "requestId", requestId,
                "tenant", currentTenant,
                "data", dto);

        try {
            kafkaProducerHelper.sendMessage(employeeSaveTopic, requestId, messagePayload);
        } catch (Exception e) {
            kafkaProducerHelper.sendMessage(employeeSaveTopic, requestId, Map.of(
                    "requestId", requestId,
                    "error", e.getMessage()
            ));
        }
    }
}
