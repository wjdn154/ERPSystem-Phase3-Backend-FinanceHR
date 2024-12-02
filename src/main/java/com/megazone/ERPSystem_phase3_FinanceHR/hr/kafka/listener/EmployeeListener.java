package com.megazone.ERPSystem_phase3_FinanceHR.hr.kafka.listener;

import com.megazone.ERPSystem_phase3_FinanceHR.common.config.multi_tenant.TenantContext;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.KafkaProducerHelper;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.attendance_management.dto.EmployeeAttendanceDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.dto.EmployeeOneDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.service.attendance_management.Attendance.AttendanceService;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.service.basic_information_management.Employee.EmployeeService;
import io.jsonwebtoken.lang.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmployeeListener {
    private final EmployeeService employeeService;
    private final KafkaProducerHelper kafkaProducerHelper;
    private final AttendanceService attendanceService;


    @KafkaListener(topics = "employee-showOne-request", groupId = "employee-service-group")
    public void handleEmployeeShowOne(Map<String, Object> request) {
        String requestId = String.valueOf(request.get("requestId"));
        String currentTenant = String.valueOf(request.get("currentTenant"));
        Long data = Long.valueOf((Integer) request.get("employeeId"));

        try {
            TenantContext.setCurrentTenant(currentTenant);

            EmployeeOneDTO employeeOneDTO = employeeService.findEmployeeById(data).orElseThrow(
                    () -> new RuntimeException("해당 사원을 찾을 수 없습니다."));
            kafkaProducerHelper.sendMessage("employee-showOne-response", requestId, Map.of(
                    "requestId", requestId,
                    "data", employeeOneDTO));

        }
        catch (Exception e) {
            kafkaProducerHelper.sendMessage("employee-showOne-response", requestId, Map.of(
                    "requestId", requestId,
                    "error", e.getMessage()));
        }
        finally {
            TenantContext.setCurrentTenant("PUBLIC");
        }
    }

    @KafkaListener(topics = "employee-showList-request", groupId = "employee-service-group")
    public void handleEmployeeShowList(Map<String, Object> request) {
        String requestId = String.valueOf(request.get("requestId"));
        String currentTenant = String.valueOf(request.get("currentTenant"));
        List<Long> employeeIdList = (List<Long>) request.get("employeeId");

        try {
            TenantContext.setCurrentTenant(currentTenant);

            List<EmployeeOneDTO> employeeList = employeeService.findWorkerAll(employeeIdList);

            kafkaProducerHelper.sendMessage("employee-showList-response", requestId, Map.of(
                    "requestId", requestId,
                    "data", employeeList
            ));
        }
        catch (Exception e) {
            kafkaProducerHelper.sendMessage("employee-showList-response", requestId, Map.of(
                    "requestId", requestId,
                    "error", e.getMessage()));
        }
        finally {
            TenantContext.setCurrentTenant("PUBLIC");
        }
    }

    @KafkaListener(topics = "employee-attendanceList-request", groupId = "employee-service-group")
    public void handleEmployeeAttendanceShowList(Map<String, Object> request) {
        String requestId = String.valueOf(request.get("requestId"));
        String currentTenant = String.valueOf(request.get("currentTenant"));
        Long employeeId = Long.valueOf((Integer) request.get("employeeId"));

        try {
            TenantContext.setCurrentTenant(currentTenant);

            List<EmployeeAttendanceDTO> employeeList = attendanceService.getEmployeeAttendanceList(employeeId);
            kafkaProducerHelper.sendMessage("employee-attendanceList-response", requestId, Map.of(
                    "requestId", requestId,
                    "data", employeeList));

        }
        catch (Exception e) {
            kafkaProducerHelper.sendMessage("employee-attendanceList-response", requestId, Map.of(
                    "requestId", requestId,
                    "error",e.getMessage()));
        }
        finally {
            TenantContext.setCurrentTenant("PUBLIC");
        }
    }

}
