package com.megazone.ERPSystem_phase3_FinanceHR.hr.repository.basic_information_management.Permission;

import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long>, PermissionRepositoryCustom {
    //Optional<Permission> findById(Long id);

}
