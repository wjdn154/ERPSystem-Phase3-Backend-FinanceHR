package com.megazone.ERPSystem_phase3_FinanceHR.hr.service.basic_information_management.salary;

import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_configuration.dto.SalaryEntryDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_configuration.dto.SalaryShowDto;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.salary.Salary;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.basic_configuration.SalaryStepRepository;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.basic_information_management.salary.LongTermCareInsurancePensionRepository;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.basic_information_management.salary.SalaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;
    private final SalaryStepRepository salaryStepRepository;
    private final LongTermCareInsurancePensionRepository longTermCareInsurancePensionRepository;

    @Override
    public void saveSalary(SalaryEntryDTO dto) {
        Salary salary = new Salary();

        salary.setEmployeeId(
                employeeRepository.findById(dto.getEmployeeId()).orElseThrow(
                        () -> new NoSuchElementException("해당하는 사원이 없습니다.")).getId());
        salary.setSalaryStep(
                salaryStepRepository.findById(dto.getSalaryStepId()).orElseThrow(
                        () -> new NoSuchElementException("해당하는 호봉 정보가 없습니다.")));
        salary.setSalaryType(dto.getSalaryType());
        salary.setIncomeTaxType(dto.isIncomeTaxType());
        salary.setStudentLoanRepaymentStatus(dto.isStudentLoanRepaymentStatus());
        salary.setStudentLoanRepaymentAmount(dto.getStudentLoanRepaymentAmount());
        salary.setPensionType(dto.getPensionType());
        salary.setNationalPensionAmount(dto.getNationalPensionAmount());
        salary.setPrivateSchoolPensionAmount(dto.getPrivateSchoolPensionAmount());
        salary.setHealthInsurancePensionAmount(dto.getHealthInsurancePensionAmount());
        salary.setHealthInsuranceNumber(dto.getHealthInsuranceNumber());
        salary.setLongTermCareInsurancePensionCode(dto.getLongTermCareInsurancePensionCode());
        salary.setEmploymentInsuranceAmount(dto.getEmploymentInsuranceAmount());
        salary.setUnionMembershipStatus(dto.isUnionMembershipStatus());

        salaryRepository.save(salary);
    }

    @Override
    @Transactional(readOnly = true)
    public SalaryShowDto show(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new NoSuchElementException("해당하는 사원이 없습니다."));

        Salary result = salaryRepository.findByEmployeeId(employeeId);
        String description = longTermCareInsurancePensionRepository.findByCode(result.getLongTermCareInsurancePensionCode()).getDescription();

        return SalaryShowDto.create(result, description);
    }
}
