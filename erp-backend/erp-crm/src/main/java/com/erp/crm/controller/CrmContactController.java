package com.erp.crm.controller;
import com.erp.common.result.Result;
import com.erp.crm.entity.CrmContact;
import com.erp.crm.service.CrmContactService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Tag(name = "联系人管理") @RestController @RequestMapping("/crm/contact") @RequiredArgsConstructor
public class CrmContactController {
    private final CrmContactService crmContactService;
    @GetMapping("/list") public Result<List<CrmContact>> list() { return Result.success(crmContactService.list()); }
    @PostMapping @PreAuthorize("hasAuthority('crm:contact:add')") public Result<Void> add(@RequestBody CrmContact c) { crmContactService.save(c); return Result.success(); }
    @DeleteMapping("/{id}") @PreAuthorize("hasAuthority('crm:contact:remove')") public Result<Void> remove(@PathVariable Long id) { crmContactService.removeById(id); return Result.success(); }
}
