package com.erp.production.controller;
import com.erp.common.result.Result;
import com.erp.production.entity.WorkReport;
import com.erp.production.service.WorkReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Tag(name = "工序报工管理") @RestController @RequestMapping("/production/report") @RequiredArgsConstructor
public class WorkReportController {
    private final WorkReportService workReportService;
    @GetMapping("/list") public Result<List<WorkReport>> list() { return Result.success(workReportService.list()); }
    @PostMapping @PreAuthorize("hasAuthority('production:report:add')") public Result<Void> add(@RequestBody WorkReport report) { workReportService.save(report); return Result.success(); }
}
