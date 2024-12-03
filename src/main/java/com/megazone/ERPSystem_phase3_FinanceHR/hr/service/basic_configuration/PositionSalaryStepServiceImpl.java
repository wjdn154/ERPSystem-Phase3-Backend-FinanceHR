package com.megazone.ERPSystem_phase3_FinanceHR.hr.service.basic_configuration;

import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_configuration.dto.*;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.basic_configuration.PositionSalaryStepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PositionSalaryStepServiceImpl implements PositionSalaryStepService {
    private final PositionSalaryStepRepository positionSalaryStepRepository;

    @Override
    @Transactional(readOnly = true)
    public PositionSalaryStepShowAllDTO show(Long positionId) {

        List<PositionSalaryStepDTO> queryResults = positionSalaryStepRepository.show(positionId);
        PositionSalaryStepShowAllDTO result = showCreate(queryResults);
        result.setPositionSalaryStepDateDetailDTOList(positionSalaryStepRepository.dateList(positionId));
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public PositionSalaryStepShowAllDTO endDateShow(PositionSalaryStepSearchDTO dto) {
        List<PositionSalaryStepDTO> queryResults = positionSalaryStepRepository.endDateShow(dto);
        return showCreate(queryResults);
    }

    public PositionSalaryStepShowAllDTO showCreate(List<PositionSalaryStepDTO> queryResults) {

        Map<Long, PositionSalaryStepShowDTO> maps = new HashMap<>();

        for(PositionSalaryStepDTO dto : queryResults) {
            PositionSalaryStepShowDTO resultDto;
            if(maps.containsKey(dto.getSalaryStepId())) {
                resultDto = maps.get(dto.getSalaryStepId());
            }
            else {
                maps.put(dto.getSalaryStepId(), PositionSalaryStepShowDTO.create(dto));
                resultDto = maps.get(dto.getSalaryStepId());
                resultDto.setAllowances(new ArrayList<>());
            }
            resultDto.getAllowances().add(
                    PositionSalaryStepAllowanceDetailDTO.create(
                            dto.getAllowanceId(),
                            dto.getAllowanceName(),
                            dto.getAmount()
                    ));
            BigDecimal totalAmount = resultDto.getTotalAllowance().add(dto.getAmount());
            resultDto.setTotalAllowance(totalAmount);
        }

        List<PositionSalaryStepShowDTO> result = new ArrayList<>(maps.values());
        Collections.sort(result, Comparator.comparing(PositionSalaryStepShowDTO::getPositionSalaryStepId));

        return PositionSalaryStepShowAllDTO.create(result,null);
    }
}
