package com.erp.hr.controller;
import com.erp.common.result.Result;
import com.erp.hr.entity.Payroll;
import com.erp.hr.service.PayrollService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Tag(name = "薪资管理") @RestController @RequestMapping("/hr/payroll") @RequiredArgsConstructor
public class PayrollController {
    private final PayrollService payrollService;
    @GetMapping("/list") public Result<List<Payroll>> list() { return Result.success(payrollService.list()); }
    @PostMapping @PreAuthorize("hasAuthority('hr:payroll:add')") public Result<Void> add(@RequestBody Payroll p) { payrollService.save(p); return Result.success(); }
    @PutMapping @PreAuthorize("hasAuthority('hr:payroll:edit')") public Result<Void> edit(@RequestBody Payroll p) { payrollService.updateById(p); return Result.success(); }
}
