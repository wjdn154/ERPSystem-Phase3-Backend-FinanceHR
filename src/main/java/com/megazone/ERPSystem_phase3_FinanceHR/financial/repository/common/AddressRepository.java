package com.megazone.ERPSystem_phase3_FinanceHR.financial.repository.common;

import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.common.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}