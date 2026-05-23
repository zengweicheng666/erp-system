package com.erp.purchase.controller;
import com.erp.common.result.Result;
import com.erp.common.result.PageResult;
import com.erp.purchase.dto.OrderQuery;
import com.erp.purchase.entity.PurchaseInbound;
import com.erp.purchase.service.PurchaseInboundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Tag(name = "采购入库管理")
@RestController
@RequestMapping("/purchase/inbound")
@RequiredArgsConstructor
public class PurchaseInboundController {
    private final PurchaseInboundService purchaseInboundService;

    @Operation(summary = "采购入库列表")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('purchase:inbound:list')")
    public Result<PageResult<PurchaseInbound>> list(OrderQuery q) { return Result.success(purchaseInboundService.listInbounds(q)); }

    @Operation(summary = "执行采购入库")
    @PostMapping
    @PreAuthorize("hasAuthority('purchase:inbound:add')")
    public Result<Void> inbound(@RequestBody Map<String, Object> params) {
        Long orderId = Long.valueOf(params.get("orderId").toString());
        Long warehouseId = Long.valueOf(params.get("warehouseId").toString());
        String remark = (String) params.get("remark");
        purchaseInboundService.inbound(orderId, warehouseId, remark);
        return Result.success();
    }
}
