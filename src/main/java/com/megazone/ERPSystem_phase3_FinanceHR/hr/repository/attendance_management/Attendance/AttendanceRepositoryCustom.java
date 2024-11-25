package com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.attendance_management.Attendance;

import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.attendance_management.dto.EmployeeAttendanceDTO;

import java.util.List;

public interface AttendanceRepositoryCustom {
    List<EmployeeAttendanceDTO> findAttendanceList(Long searchEmployeeId);
}
