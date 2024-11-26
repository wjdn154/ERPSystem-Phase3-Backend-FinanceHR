package com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.attendance_management.Attendance;

import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.attendance_management.QAttendance;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.attendance_management.dto.EmployeeAttendanceDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.QEmployee;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AttendanceRepositoryImpl implements AttendanceRepositoryCustom{
private final JPAQueryFactory queryFactory;

    @Override
    public List<EmployeeAttendanceDTO> findAttendanceList(Long searchEmployeeId) {
        QAttendance attendance = QAttendance.attendance;
        QEmployee employee = QEmployee.employee;
        return queryFactory
                .select(attendance.id,
                        attendance.employee.id,
                        attendance.employee.lastName.concat(attendance.employee.firstName),
                        attendance.employee.employeeNumber,
                        attendance.attendanceCode,
                        attendance.employee.position.id,
                        attendance.employee.position.positionName,
                        attendance.date,
                        attendance.checkInTime,
                        attendance.checkOutTime,
                        attendance.status)
                .from(attendance)
                .join(attendance.employee, employee) // 명시적 조인 추가
                .where(attendance.employee.id.eq(searchEmployeeId))
                .fetch()
                .stream()
                .map(tuple -> new EmployeeAttendanceDTO(
                        tuple.get(attendance.id),
                        tuple.get(attendance.employee.id),
                        tuple.get(attendance.employee.lastName.concat(attendance.employee.firstName)), // 별칭으로 접근
                        tuple.get(attendance.employee.employeeNumber),
                        tuple.get(attendance.attendanceCode),
                        tuple.get(attendance.employee.position.id),
                        tuple.get(attendance.employee.position.positionName),
                        tuple.get(attendance.date),
                        tuple.get(attendance.checkInTime),
                        tuple.get(attendance.checkOutTime),
                        tuple.get(attendance.status)
                ))
                .collect(Collectors.toList());
    }
}
