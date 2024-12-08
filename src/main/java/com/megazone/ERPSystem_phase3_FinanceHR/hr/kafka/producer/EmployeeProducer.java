package com.megazone.ERPSystem_phase3_FinanceHR.hr.kafka.producer;

import com.megazone.ERPSystem_phase3_FinanceHR.common.config.multi_tenant.TenantContext;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.KafkaProducerHelper;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.dto.EmployeeShowToDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class EmployeeProducer {

    private final KafkaProducerHelper kafkaProducerHelper;
    private final String employeeUpdateTopic = "employee-update";
    private final String employeeSaveTopic = "employee-save";

    public void employeeUpdateProducer(EmployeeShowToDTO originDto, EmployeeShowToDTO updateDto) {
        String requestId = UUID.randomUUID().toString();
        String currentTenant = TenantContext.getCurrentTenant();

        Map<String, Object> messagePayload = Map.of(
                "sagaName",employeeUpdateTopic,
                "requestId", requestId,
                "tenant", currentTenant,
                "queryType", "UPDATE",
                "originData", originDto,
                "updateData",updateDto
        );

        try {
            kafkaProducerHelper.sendMessage("saga-start", requestId, messagePayload);
            System.out.println("saga-start 메시지 발행 성공 : " + employeeUpdateTopic);
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
                "data", dto,
                "targetTopic", employeeSaveTopic);

        try {
            kafkaProducerHelper.sendMessage("saga-start", requestId, messagePayload);
            System.out.println("saga-start 메시지 발행 성공 : " + employeeSaveTopic);
        } catch (Exception e) {
            kafkaProducerHelper.sendMessage(employeeSaveTopic, requestId, Map.of(
                    "requestId", requestId,
                    "error", e.getMessage()
            ));
        }
    }
}
