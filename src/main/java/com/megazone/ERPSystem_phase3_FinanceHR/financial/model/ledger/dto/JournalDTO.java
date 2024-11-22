package com.megazone.ERPSystem_phase3_FinanceHR.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JournalDTO {
    LocalDate startDate;
    LocalDate endDate;
}