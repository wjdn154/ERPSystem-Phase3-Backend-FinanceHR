package com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.dto;

import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.enums.EmploymentStatus;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.enums.EmploymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDataDTO {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String hireDate;
    private EmploymentStatus employmentStatus;
    private EmploymentType employmentType;
    private String email;
    private String address;
    private String registrationNumber;
    private boolean householdHead;
    private String profilePicture;
    private Long departmentId;
    private String departmentCode;
    private String departmentName;
    private Long positionId;
    private String positionCode;
    private String positionName;
    private Long titleId;
    private String titleCode;
    private String titleName;
    private Long bankId;
    private String name;
    private String code;
    private String accountNumber;  // 계좌 번호

    public static EmployeeDataDTO create(Employee employee) {
        return new EmployeeDataDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getPhoneNumber(),
                employee.getHireDate().toString(),
                employee.getEmploymentStatus(),
                employee.getEmploymentType(),
                employee.getEmail(),
                employee.getAddress(),
                employee.getRegistrationNumber(),
                employee.isHouseholdHead(),
                employee.getImagePath(),
                employee.getDepartment().getId(),
                employee.getDepartment().getDepartmentCode(),
                employee.getDepartment().getDepartmentName(),
                employee.getPosition().getId(),
                employee.getPosition().getPositionCode(),
                employee.getPosition().getPositionName(),
                employee.getJobTitle().getId(),
                employee.getJobTitle().getJobTitleCode(),
                employee.getJobTitle().getJobTitleName(),
                employee.getBankAccount().getBank().getId(),
                employee.getBankAccount().getBank().getName(),
                employee.getBankAccount().getBank().getCode(),
                employee.getBankAccount().getAccountNumber());
    }
}