package com.erp.crm.controller;
import com.erp.common.result.Result;
import com.erp.common.result.PageResult;
import com.erp.crm.entity.CrmLead;
import com.erp.crm.service.CrmLeadService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@Tag(name = "销售线索管理") @RestController @RequestMapping("/crm/lead") @RequiredArgsConstructor
public class CrmLeadController {
    private final CrmLeadService crmLeadService;
    @GetMapping("/list") @PreAuthorize("hasAuthority('crm:lead:list')") public Result<PageResult<CrmLead>> list(@RequestParam(defaultValue="1") Long page, @RequestParam(defaultValue="10") Long size, String name, Integer status) { return Result.success(crmLeadService.listLeads(name, status, page, size)); }
    @GetMapping("/{id}") public Result<CrmLead> get(@PathVariable Long id) { return Result.success(crmLeadService.getById(id)); }
    @PostMapping @PreAuthorize("hasAuthority('crm:lead:add')") public Result<Void> add(@RequestBody CrmLead lead) { crmLeadService.save(lead); return Result.success(); }
    @PutMapping @PreAuthorize("hasAuthority('crm:lead:edit')") public Result<Void> edit(@RequestBody CrmLead lead) { crmLeadService.updateById(lead); return Result.success(); }
    @DeleteMapping("/{id}") @PreAuthorize("hasAuthority('crm:lead:remove')") public Result<Void> remove(@PathVariable Long id) { crmLeadService.removeById(id); return Result.success(); }
}
