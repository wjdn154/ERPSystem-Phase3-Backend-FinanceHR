package com.megazone.ERPSystem_phase3_FinanceHR.financial.repository.basic_information_management.client;

import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.basic_information_management.client.ManagerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManageInfoRepository extends JpaRepository<ManagerInfo, Long> {
}