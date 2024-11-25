package com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.basic_information_management.Employee;

import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.*;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.dto.BankAccountDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.dto.EmployeeOneDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Employee> findAllByUser() {
        QEmployee employee = QEmployee.employee;
        QUsers users = QUsers.users;

        return queryFactory
                .select(employee)
                .from(employee)
                .join(users).on(employee.email.eq(users.employee.email))
                .fetch();
    }

    @Override
    public List<EmployeeOneDTO> findWorkerAll(List<Long> searchId) {
        QEmployee employee = QEmployee.employee;
        QDepartment department = QDepartment.department;
        QPosition position = QPosition.position;
        QJobTitle jobTitle = QJobTitle.jobTitle;

        return queryFactory.select(
                Projections.constructor(EmployeeOneDTO.class,
                        employee.id,
                        employee.employeeNumber,
                        employee.firstName,
                        employee.lastName,
                        employee.registrationNumber,
                        employee.phoneNumber,
                        employee.employmentStatus,
                        employee.employmentType,
                        employee.email,
                        employee.address,
                        employee.hireDate,
                        employee.isHouseholdHead,
                        employee.imagePath,
                        department.id,
                        department.departmentCode,
                        department.departmentName,
                        position.id,
                        position.positionCode,
                        position.positionName,
                        jobTitle.id,
                        jobTitle.jobTitleCode,
                        jobTitle.jobTitleName,
                        Expressions.nullExpression(BankAccountDTO.class)))
                .from(employee)
                .join(employee.department,department)
                .join(employee.position,position)
                .join(employee.jobTitle,jobTitle)
                .where(employee.id.in(searchId))
                .fetch();
    }
}
