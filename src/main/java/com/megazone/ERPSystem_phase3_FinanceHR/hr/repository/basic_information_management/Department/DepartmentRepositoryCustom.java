package com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.basic_information_management.Department;

import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.dto.DepartmentShowDTO;

import java.util.List;

public interface DepartmentRepositoryCustom {
    List<DepartmentShowDTO> findAllDepartments();
}
