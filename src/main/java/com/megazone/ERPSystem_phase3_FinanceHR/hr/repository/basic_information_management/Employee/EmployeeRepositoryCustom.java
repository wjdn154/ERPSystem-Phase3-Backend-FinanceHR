package com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.basic_information_management.Employee;

import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.Employee;

import java.util.List;

public interface EmployeeRepositoryCustom {
    List<Employee> findAllByUser();

}
