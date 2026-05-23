package com.erp.production.controller;
import com.erp.common.result.Result;
import com.erp.production.entity.ProcessRoute;
import com.erp.production.service.ProcessRouteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Tag(name = "工艺路线管理") @RestController @RequestMapping("/production/process") @RequiredArgsConstructor
public class ProcessRouteController {
    private final ProcessRouteService processRouteService;
    @GetMapping("/list") public Result<List<ProcessRoute>> list() { return Result.success(processRouteService.list()); }
    @PostMapping @PreAuthorize("hasAuthority('production:process:add')") public Result<Void> add(@RequestBody ProcessRoute route) { processRouteService.save(route); return Result.success(); }
    @DeleteMapping("/{id}") @PreAuthorize("hasAuthority('production:process:remove')") public Result<Void> remove(@PathVariable Long id) { processRouteService.removeById(id); return Result.success(); }
}
