package com.megazone.ERPSystem_phase3_FinanceHR.hr.service.attendance_management.Leaves;

import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.attendance_management.Leaves;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.attendance_management.LeavesType;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.attendance_management.dto.LeavesAllShowDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.attendance_management.dto.LeavesCreateDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.attendance_management.dto.LeavesShowDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.attendance_management.Leaves.LeavesRepository;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.attendance_management.LeavesType.LeavesTypeRepository;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.basic_information_management.Employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LeavesServiceImpl implements LeavesService{

    private final LeavesRepository leavesRepository;
    private final EmployeeRepository employeeRepository;
    private final LeavesTypeRepository leavesTypeRepository;

    public LeavesShowDTO createLeave(LeavesCreateDTO createDTO) {
        // 사원과 휴가 유형 조회
        Employee employee = employeeRepository.findById(createDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("사원을 찾을 수 없습니다"));
        LeavesType leavesType = leavesTypeRepository.findById(createDTO.getLeavesTypeId())
                .orElseThrow(() -> new RuntimeException("휴가 유형을 찾을 수 없습니다"));

        // Leaves 엔티티 생성
        Leaves leave = new Leaves();
        leave.setEmployee(employee);
        leave.setLeavesType(leavesType);
        leave.setCode(createDTO.getCode());
        leave.setStartDate(createDTO.getStartDate());
        leave.setEndDate(createDTO.getEndDate());
        leave.setReason(createDTO.getReason());
        leave.setStatus(createDTO.getStatus());

        // 저장
        Leaves savedLeave = leavesRepository.save(leave);

        // 저장된 엔티티를 DTO로 변환
        return new LeavesShowDTO(
                savedLeave.getId(),
                savedLeave.getEmployee().getLastName() + " " + savedLeave.getEmployee().getFirstName(),
                leavesType.getTypeName(),
                savedLeave.getCode(),
                savedLeave.getStartDate(),
                savedLeave.getEndDate(),
                savedLeave.getReason(),
                savedLeave.getStatus()
        );
    }

    // 휴가 리스트 조회
    @Transactional(readOnly = true)
    public List<LeavesAllShowDTO> getLeavesList() {
        List<Leaves> leavesList = leavesRepository.findAll();
        return leavesList.stream()
                .map(LeavesAllShowDTO::fromEntity) // 엔티티를 DTO로 변환
                .collect(Collectors.toList());
    }
}
