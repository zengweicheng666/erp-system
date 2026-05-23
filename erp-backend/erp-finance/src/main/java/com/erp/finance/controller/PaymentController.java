package com.erp.finance.controller;
import com.erp.common.result.Result;
import com.erp.common.result.PageResult;
import com.erp.finance.dto.FinanceQuery;
import com.erp.finance.entity.Payment;
import com.erp.finance.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "付款单管理")
@RestController
@RequestMapping("/finance/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('finance:payment:list')")
    public Result<PageResult<Payment>> list(FinanceQuery q) { return Result.success(paymentService.listPayments(q)); }
    @PostMapping
    @PreAuthorize("hasAuthority('finance:payment:add')")
    public Result<Void> add(@RequestBody Payment p) { paymentService.createPayment(p); return Result.success(); }
}
