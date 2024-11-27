package com.megazone.ERPSystem_phase3_FinanceHR.financial.model.basic_information_management.company;

import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.basic_information_management.company.enums.EntityType;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.common.Contact;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * 회사 기본 정보 테이블
 * 회사 기본 정보 등록시 사용하는 테이블
 */
@Entity(name = "company")
@Table(name = "company")
@NoArgsConstructor
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유식별자

    @Column(nullable = false) // 회사명
    private String name;

    @Column(nullable = false) // 관리자 이메일
    private String adminUsername;

}