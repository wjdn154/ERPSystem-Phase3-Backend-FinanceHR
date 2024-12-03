package com.megazone.ERPSystem_phase3_FinanceHR.hr.service.salary_ledger;

import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.salary.IncomeTax;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.salary.dto.IncomeTaxShowDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.basic_information_management.salary_ledger.IncomeTaxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class IncomeTaxServiceImpl implements IncomeTaxService {
    private final IncomeTaxRepository incomeTaxRepository;


    @Override
    @Transactional(readOnly = true)
    public List<IncomeTax> showBaseIncomeTax() {
        return incomeTaxRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal incomeTaxCalculator(BigDecimal amount) {

        List<IncomeTax> taxTable = incomeTaxRepository.findAll();

        for (int i = 0; i < taxTable.size(); i++) {
            IncomeTax tax = taxTable.get(i);

            if (i == taxTable.size() - 1) { // 마지막 인덱스일 때
                    BigDecimal incomeTaxRate = tax.getRate();
                    return incomeTaxRate.multiply(amount).setScale(0, RoundingMode.HALF_UP);
            } else { // 마지막 인덱스가 아닐 때
                if (tax.getLowAmount().compareTo(amount) <= 0 && tax.getHighAmount().compareTo(amount) >= 0) {
                    BigDecimal incomeTaxRate = tax.getRate();
                    return incomeTaxRate.multiply(amount).setScale(0, RoundingMode.HALF_UP);
                }
            }
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<IncomeTaxShowDTO> show() {
        return incomeTaxRepository.findAll().stream().map(
                one -> IncomeTaxShowDTO.create(one)
        ).collect(Collectors.toList());
    }
}
