package com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.dto;

import com.megazone.ERPSystem_phase3_FinanceHR.hr.model.basic_information_management.employee.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeUpdateDTO {
    private EmployeeDataDTO dataDTO;
    private MultipartFile imageFile;

    public static EmployeeUpdateDTO create(EmployeeDataDTO dataDTO, MultipartFile imageFile) {
        return new EmployeeUpdateDTO(dataDTO, imageFile);
    }

//    public static EmployeeUpdateDTO create(Employee employee) {
//        return new EmployeeUpdateDTO(EmployeeDataDTO.create(employee),employee.getImagePath());
//    }
}
