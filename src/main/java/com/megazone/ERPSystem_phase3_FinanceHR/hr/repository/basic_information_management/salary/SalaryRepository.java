package com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.basic_information_management.salary;

import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.salary.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, Long> , SalaryRepositoryCustom{
    Salary findByEmployeeId(Long employeeId);
}
