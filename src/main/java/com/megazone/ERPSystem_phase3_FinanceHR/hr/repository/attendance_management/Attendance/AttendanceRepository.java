package com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.attendance_management.Attendance;

import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.attendance_management.Attendance;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.attendance_management.dto.EmployeeAttendanceDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>, AttendanceRepositoryCustom{

    Optional<Attendance> findById(Long id); //특정 직원의 출퇴근 기록을 조회하는 메소드


    Optional<Attendance> findByEmployeeIdAndDate(Long employeeId, LocalDate date);

    void delete(Attendance attendance);

    void deleteByEmployeeId(Long employeeId);

}
