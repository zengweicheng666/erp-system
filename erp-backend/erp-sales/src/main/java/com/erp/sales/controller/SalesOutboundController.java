package com.erp.sales.controller;
import com.erp.common.result.Result;
import com.erp.common.result.PageResult;
import com.erp.sales.dto.SalesOrderQuery;
import com.erp.sales.entity.SalesOutbound;
import com.erp.sales.service.SalesOutboundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Tag(name = "销售出库管理")
@RestController
@RequestMapping("/sales/outbound")
@RequiredArgsConstructor
public class SalesOutboundController {
    private final SalesOutboundService salesOutboundService;

    @Operation(summary = "销售出库列表")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sales:outbound:list')")
    public Result<PageResult<SalesOutbound>> list(SalesOrderQuery q) { return Result.success(salesOutboundService.listOutbounds(q)); }

    @Operation(summary = "执行销售出库")
    @PostMapping
    @PreAuthorize("hasAuthority('sales:outbound:add')")
    public Result<Void> outbound(@RequestBody Map<String, Object> params) {
        Long orderId = Long.valueOf(params.get("orderId").toString());
        Long warehouseId = Long.valueOf(params.get("warehouseId").toString());
        String remark = (String) params.get("remark");
        salesOutboundService.outbound(orderId, warehouseId, remark);
        return Result.success();
    }
}
