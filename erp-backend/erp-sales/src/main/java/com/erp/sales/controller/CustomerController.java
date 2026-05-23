package com.erp.sales.controller;
import com.erp.common.result.Result;
import com.erp.common.result.PageResult;
import com.erp.sales.dto.CustomerQuery;
import com.erp.sales.entity.Customer;
import com.erp.sales.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "客户管理")
@RestController
@RequestMapping("/sales/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @Operation(summary = "分页查询客户")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sales:customer:list')")
    public Result<PageResult<Customer>> list(CustomerQuery q) { return Result.success(customerService.listCustomers(q)); }
    @GetMapping("/{id}")
    public Result<Customer> get(@PathVariable Long id) { return Result.success(customerService.getById(id)); }
    @PostMapping
    @PreAuthorize("hasAuthority('sales:customer:add')")
    public Result<Void> add(@RequestBody Customer c) { customerService.createCustomer(c); return Result.success(); }
    @PutMapping
    @PreAuthorize("hasAuthority('sales:customer:edit')")
    public Result<Void> edit(@RequestBody Customer c) { customerService.updateCustomer(c); return Result.success(); }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sales:customer:remove')")
    public Result<Void> remove(@PathVariable Long id) { customerService.removeById(id); return Result.success(); }
}
