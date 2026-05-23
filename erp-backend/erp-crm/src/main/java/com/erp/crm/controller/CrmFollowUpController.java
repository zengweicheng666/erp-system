package com.erp.crm.controller;
import com.erp.common.result.Result;
import com.erp.crm.entity.CrmFollowUp;
import com.erp.crm.service.CrmFollowUpService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Tag(name = "跟进记录管理") @RestController @RequestMapping("/crm/followup") @RequiredArgsConstructor
public class CrmFollowUpController {
    private final CrmFollowUpService crmFollowUpService;
    @GetMapping("/list") public Result<List<CrmFollowUp>> list() { return Result.success(crmFollowUpService.list()); }
    @PostMapping @PreAuthorize("hasAuthority('crm:followup:add')") public Result<Void> add(@RequestBody CrmFollowUp f) { crmFollowUpService.save(f); return Result.success(); }
}
