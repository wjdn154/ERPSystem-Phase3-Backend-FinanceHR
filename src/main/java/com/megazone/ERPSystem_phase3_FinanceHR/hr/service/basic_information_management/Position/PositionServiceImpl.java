package com.megazone.ERPSystem_phase3_FinanceHR.hr.service.basic_information_management.Position;

import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.Position;
import com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.basic_information_management.Position.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PositionServiceImpl implements PositionService {
    private final PositionRepository positionRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Position> getPositionById(Long id) {
        return positionRepository.findById(id);
    }
}
