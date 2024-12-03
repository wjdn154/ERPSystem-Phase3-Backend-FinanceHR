package com.megazone.ERPSystem_phase3_FinanceHR.hr.service.basic_configuration;

import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_configuration.SalaryStep;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_configuration.dto.SalaryStepEntryDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_configuration.dto.SalaryStepShowDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.basic_configuration.SalaryStepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SalaryStepServiceImpl implements SalaryStepService {
    private final SalaryStepRepository salaryStepRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SalaryStepShowDTO> show() {
        return salaryStepRepository.findAll().stream().map(
                salaryStep -> SalaryStepShowDTO.create(salaryStep)).toList();
    }

    @Override
    public String entry(SalaryStepEntryDTO dto) {
        salaryStepRepository.save(createSalarystep(dto));

        return "등록완료";
    }

    public SalaryStep createSalarystep(SalaryStepEntryDTO dto) {
        SalaryStep salaryStep = new SalaryStep();

        SalaryStep lastSalaryStep = salaryStepRepository.findTopByOrderByIdDesc().orElseThrow(
                () -> new RuntimeException("해당하는 호봉을 찾을수 없습니다."));

        String newCode = String.valueOf(Integer.parseInt(lastSalaryStep.getCode()) + 1);

        salaryStep.setName(dto.getName());
        salaryStep.setCode(newCode);
        salaryStep.setDescription(dto.getDescription());

        return salaryStep;
    }
}
