package com.erp.hr.controller;
import com.erp.common.result.Result;
import com.erp.hr.entity.SysDept;
import com.erp.hr.service.SysDeptService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Tag(name = "部门管理") @RestController @RequestMapping("/hr/dept") @RequiredArgsConstructor
public class SysDeptController {
    private final SysDeptService sysDeptService;
    @GetMapping("/tree") @PreAuthorize("hasAuthority('hr:dept:list')") public Result<List<SysDept>> tree() { return Result.success(sysDeptService.buildTree(sysDeptService.listDepts())); }
    @PostMapping @PreAuthorize("hasAuthority('hr:dept:add')") public Result<Void> add(@RequestBody SysDept dept) { sysDeptService.save(dept); return Result.success(); }
    @PutMapping @PreAuthorize("hasAuthority('hr:dept:edit')") public Result<Void> edit(@RequestBody SysDept dept) { sysDeptService.updateById(dept); return Result.success(); }
    @DeleteMapping("/{id}") @PreAuthorize("hasAuthority('hr:dept:remove')") public Result<Void> remove(@PathVariable Long id) { sysDeptService.removeById(id); return Result.success(); }
}
