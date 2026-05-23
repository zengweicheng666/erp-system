package com.erp.crm.controller;
import com.erp.common.result.Result;
import com.erp.common.result.PageResult;
import com.erp.crm.entity.CrmOpportunity;
import com.erp.crm.service.CrmOpportunityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@Tag(name = "商机管理") @RestController @RequestMapping("/crm/opportunity") @RequiredArgsConstructor
public class CrmOpportunityController {
    private final CrmOpportunityService crmOpportunityService;
    @GetMapping("/list") @PreAuthorize("hasAuthority('crm:opportunity:list')") public Result<PageResult<CrmOpportunity>> list(@RequestParam(defaultValue="1") Long page, @RequestParam(defaultValue="10") Long size, Long customerId) { return Result.success(crmOpportunityService.listOpportunities(customerId, page, size)); }
    @PostMapping @PreAuthorize("hasAuthority('crm:opportunity:add')") public Result<Void> add(@RequestBody CrmOpportunity opp) { crmOpportunityService.save(opp); return Result.success(); }
    @PutMapping @PreAuthorize("hasAuthority('crm:opportunity:edit')") public Result<Void> edit(@RequestBody CrmOpportunity opp) { crmOpportunityService.updateById(opp); return Result.success(); }
    @DeleteMapping("/{id}") @PreAuthorize("hasAuthority('crm:opportunity:remove')") public Result<Void> remove(@PathVariable Long id) { crmOpportunityService.removeById(id); return Result.success(); }
}
