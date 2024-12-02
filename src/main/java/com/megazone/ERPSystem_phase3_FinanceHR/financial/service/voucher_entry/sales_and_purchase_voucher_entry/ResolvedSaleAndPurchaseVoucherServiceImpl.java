package com.megazone.ERPSystem_phase3_FinanceHR.financial.service.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase3_FinanceHR.Integrated.model.dashboard.dto.RecentActivityEntryDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.Integrated.model.dashboard.enums.ActivityType;
import com.megazone.ERPSystem_phase3_FinanceHR.Integrated.model.notification.dto.UserNotificationCreateAndSendDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.Integrated.model.notification.enums.ModuleType;
import com.megazone.ERPSystem_phase3_FinanceHR.Integrated.model.notification.enums.NotificationType;
import com.megazone.ERPSystem_phase3_FinanceHR.Integrated.model.notification.enums.PermissionType;
import com.megazone.ERPSystem_phase3_FinanceHR.Integrated.model.service.IntegratedService;
import com.megazone.ERPSystem_phase3_FinanceHR.Integrated.model.service.NotificationService;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.general_voucher_entry.ResolvedVoucher;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.general_voucher_entry.enums.VoucherKind;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.ResolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.ResolvedSaleAndPurchaseVoucherDeleteDTO;
import com.megazone.ERPSystem_phase3_FinanceHR.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.resolvedSaleAndPurchaseVoucher.ResolvedSaleAndPurchaseVoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional
public class ResolvedSaleAndPurchaseVoucherServiceImpl implements ResolvedSaleAndPurchaseVoucherService {

    private final ResolvedSaleAndPurchaseVoucherRepository resolvedSaleAndPurchaseVoucherRepository;
    private final IntegratedService integratedService;
    private final NotificationService notificationService;

    @Override
    @Transactional(readOnly = true)
    public List<ResolvedSaleAndPurchaseVoucher> searchAllVoucher(LocalDate date) {
        List<ResolvedSaleAndPurchaseVoucher> voucherList = new ArrayList<ResolvedSaleAndPurchaseVoucher>();

        try {
            voucherList = resolvedSaleAndPurchaseVoucherRepository.findByVoucherDateOrderByVoucherNumberAsc(date);
            if(voucherList.isEmpty()) {
                throw new NoSuchElementException("해당 날짜에 등록된 미결전표가 없습니다.");
            }
        }
        catch (NoSuchElementException e) {
            e.getStackTrace();
        }
        return voucherList;
    }

    /**
     * 미결전표가 승인되었을때 일반전표가 등록되는 메소드
     * @param unresolvedVoucherList 승인된 미결전표 List
     * @return 생성된 일반전표 반환
     */
    @Override
    public void resolvedVoucherEntry(List<UnresolvedSaleAndPurchaseVoucher> unresolvedVoucherList) {
        List<ResolvedSaleAndPurchaseVoucher> resolvedVoucherList = new ArrayList<>();
        LocalDateTime nowTime = LocalDateTime.now();
        unresolvedVoucherList
                .forEach(unresolvedVoucher -> {
                    ResolvedSaleAndPurchaseVoucher resolvedVoucher = createResolvedVoucher(unresolvedVoucher,nowTime);
                    resolvedVoucherList.add(resolvedSaleAndPurchaseVoucherRepository.save(resolvedVoucher));
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResolvedVoucher> searchEntryVoucher(Long voucherId) {
        return resolvedSaleAndPurchaseVoucherRepository.findById(voucherId).get().getResolvedVouchers();
    }

    @Override
    public BigDecimal calculateTotalAmount(List<ResolvedVoucher> vouchers, Function<ResolvedVoucher, BigDecimal> amount) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (ResolvedVoucher voucher : vouchers) {
            totalAmount = totalAmount.add(amount.apply(voucher));
        }

        return totalAmount;
    }

    @Override
    public BigDecimal totalDebit(List<ResolvedVoucher> vouchers) {
        return calculateTotalAmount(vouchers, ResolvedVoucher::getDebitAmount);
    }
    @Override
    public BigDecimal totalCredit(List<ResolvedVoucher> vouchers) {
        return calculateTotalAmount(vouchers, ResolvedVoucher::getCreditAmount);
    }

    @Override
    public String deleteVoucher(ResolvedSaleAndPurchaseVoucherDeleteDTO dto) {
        try {
            if(true) { // 전표의 담당자 이거나, 승인권자면 삭제가능 << 기능구현 필요
                dto.getSearchVoucherNumList().forEach((voucherNumber) -> {
                    resolvedSaleAndPurchaseVoucherRepository.deleteByVoucherNumberAndVoucherDate(
                            voucherNumber, dto.getSearchDate());});
                integratedService.recentActivitySave(
                        RecentActivityEntryDTO.create(
                                "매출매입전표 " + dto.getSearchVoucherNumList().size() + "건 삭제",
                                ActivityType.FINANCE));
                notificationService.createAndSend(
                        UserNotificationCreateAndSendDTO.create(
                                ModuleType.FINANCE,
                                PermissionType.USER,
                                "매출매입전표 " + dto.getSearchVoucherNumList().size() + "건 삭제",
                                NotificationType.DELETE_RESOLVED_SALEANDPURCHASE_VOUCHER));
            }
        }
        catch (Exception e) {
            e.getStackTrace();
            return null;
        }
        return "미결 매출매입전표 삭제 성공";
    }

    private ResolvedSaleAndPurchaseVoucher createResolvedVoucher(UnresolvedSaleAndPurchaseVoucher unresolvedVoucher, LocalDateTime nowTime) {
        List<ResolvedVoucher> ResolvedVouchers = unresolvedVoucher.getUnresolvedVouchers().stream()
                .map(voucher -> {return ResolvedVoucher.builder()
                        .accountSubject(voucher.getAccountSubject())
                        .transactionDescription(voucher.getTransactionDescription())
                        .voucherNumber(voucher.getVoucherNumber())
                        .voucherManager(voucher.getVoucherManager())
                        .voucherType(voucher.getVoucherType())
                        .debitAmount(voucher.getDebitAmount())
                        .creditAmount(voucher.getCreditAmount())
                        .voucherDate(voucher.getVoucherDate())
                        .client(unresolvedVoucher.getClient())
                        .voucherApprovalTime(nowTime)
                        .voucherKind(VoucherKind.SALE_AND_PURCHASE)
                        .build();}).toList();

        return ResolvedSaleAndPurchaseVoucher.builder()
                .client(unresolvedVoucher.getClient())
                .vatType(unresolvedVoucher.getVatType())
                .voucherManager(unresolvedVoucher.getVoucherManager())
                .journalEntry(unresolvedVoucher.getJournalEntry())
                .resolvedVouchers(ResolvedVouchers)
                .voucherNumber(unresolvedVoucher.getVoucherNumber())
                .voucherDate(unresolvedVoucher.getVoucherDate())
                .itemName(unresolvedVoucher.getItemName())
                .quantity(unresolvedVoucher.getQuantity())
                .unitPrice(unresolvedVoucher.getUnitPrice())
                .supplyAmount(unresolvedVoucher.getSupplyAmount())
                .vatAmount(unresolvedVoucher.getVatAmount())
                .electronicTaxInvoiceStatus(unresolvedVoucher.getElectronicTaxInvoiceStatus())
                .voucherApproveTime(nowTime)
                .build();


    }
}
