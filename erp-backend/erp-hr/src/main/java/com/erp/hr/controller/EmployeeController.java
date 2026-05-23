package com.erp.hr.controller;
import com.erp.common.result.Result;
import com.erp.common.result.PageResult;
import com.erp.hr.entity.Employee;
import com.erp.hr.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@Tag(name = "员工管理") @RestController @RequestMapping("/hr/employee") @RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    @GetMapping("/list") @PreAuthorize("hasAuthority('hr:employee:list')") public Result<PageResult<Employee>> list(@RequestParam(defaultValue="1") Long page, @RequestParam(defaultValue="10") Long size, String name, Long deptId) { return Result.success(employeeService.listEmployees(name, deptId, page, size)); }
    @GetMapping("/{id}") public Result<Employee> get(@PathVariable Long id) { return Result.success(employeeService.getById(id)); }
    @PostMapping @PreAuthorize("hasAuthority('hr:employee:add')") public Result<Void> add(@RequestBody Employee e) { employeeService.save(e); return Result.success(); }
    @PutMapping @PreAuthorize("hasAuthority('hr:employee:edit')") public Result<Void> edit(@RequestBody Employee e) { employeeService.updateById(e); return Result.success(); }
    @DeleteMapping("/{id}") @PreAuthorize("hasAuthority('hr:employee:remove')") public Result<Void> remove(@PathVariable Long id) { employeeService.removeById(id); return Result.success(); }
}
