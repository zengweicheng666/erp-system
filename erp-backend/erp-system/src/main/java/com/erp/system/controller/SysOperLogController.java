package com.erp.system.controller;

import com.erp.common.result.Result;
import com.erp.common.result.PageResult;
import com.erp.system.dto.OperLogQuery;
import com.erp.system.entity.SysOperLog;
import com.erp.system.service.SysOperLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "操作日志管理")
@RestController
@RequestMapping("/system/log")
@RequiredArgsConstructor
public class SysOperLogController {

    private final SysOperLogService sysOperLogService;

    @Operation(summary = "分页查询操作日志")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('system:log:list')")
    public Result<PageResult<SysOperLog>> list(OperLogQuery query) {
        return Result.success(sysOperLogService.listLogs(query));
    }

    @Operation(summary = "删除操作日志")
    @DeleteMapping("/{operId}")
    @PreAuthorize("hasAuthority('system:log:remove')")
    public Result<Void> remove(@PathVariable Long operId) {
        sysOperLogService.removeById(operId);
        return Result.success();
    }

    @Operation(summary = "清空操作日志")
    @DeleteMapping("/clean")
    @PreAuthorize("hasAuthority('system:log:remove')")
    public Result<Void> clean() {
        sysOperLogService.remove(null);
        return Result.success();
    }
}
