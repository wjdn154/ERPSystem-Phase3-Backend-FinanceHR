package com.megazone.ERPSystem_phase3_FinanceHR.Integrated.repository.notification;

import com.megazone.ERPSystem_phase3_FinanceHR.Integrated.model.notification.dto.UserNotificationDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.Integrated.model.notification.enums.ModuleType;
import com.megazone.ERPSystem_phase3_FinanceHR.Integrated.model.notification.enums.PermissionType;

import java.util.List;

public interface NotificationRepositoryCustom {
    List<UserNotificationDTO> fetchNotification(Long userId, ModuleType module, PermissionType permission);
}
