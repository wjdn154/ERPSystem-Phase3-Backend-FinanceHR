package com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.basic_information_management.Employee;

import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.dto.EmployeeOneDTO;

import java.util.List;

public interface EmployeeRepositoryCustom {
    List<Employee> findAllByUser();

    List<EmployeeOneDTO> findWorkerAll(List<Long> searchId);
}
