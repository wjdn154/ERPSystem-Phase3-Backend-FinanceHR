package com.megazone.ERPSystem_phase3_FinanceHR.financial.repository.basic_information_management.company;

import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.basic_information_management.company.CorporateKind;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CorporateKindRepository extends JpaRepository<CorporateKind, Long> {
    Optional<CorporateKind> findByCode(String code);
}