package com.erp.purchase.controller;
import com.erp.common.result.Result;
import com.erp.common.result.PageResult;
import com.erp.purchase.dto.OrderQuery;
import com.erp.purchase.entity.PurchaseOrder;
import com.erp.purchase.entity.PurchaseOrderItem;
import com.erp.purchase.service.PurchaseOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Tag(name = "采购订单管理")
@RestController
@RequestMapping("/purchase/order")
@RequiredArgsConstructor
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;

    @Operation(summary = "分页查询采购订单")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('purchase:order:list')")
    public Result<PageResult<PurchaseOrder>> list(OrderQuery q) { return Result.success(purchaseOrderService.listOrders(q)); }

    @Operation(summary = "获取订单详情(含明细)")
    @GetMapping("/{id}")
    public Result<PurchaseOrder> get(@PathVariable Long id) { return Result.success(purchaseOrderService.getOrderWithItems(id)); }

    @Operation(summary = "新增采购订单")
    @PostMapping
    @PreAuthorize("hasAuthority('purchase:order:add')")
    public Result<Void> add(@RequestBody Map<String, Object> params) {
        PurchaseOrder order = new PurchaseOrder();
        order.setSupplierId(Long.valueOf(params.get("supplierId").toString()));
        order.setOrderDate(java.time.LocalDateTime.now());
        order.setRemark((String) params.get("remark"));
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> items = (List<Map<String, Object>>) params.get("items");
        List<PurchaseOrderItem> orderItems = items.stream().map(m -> {
            PurchaseOrderItem i = new PurchaseOrderItem();
            i.setProductId(Long.valueOf(m.get("productId").toString()));
            i.setQuantity(Integer.valueOf(m.get("quantity").toString()));
            i.setUnitPrice(new java.math.BigDecimal(m.get("unitPrice").toString()));
            i.setTotalPrice(i.getUnitPrice().multiply(java.math.BigDecimal.valueOf(i.getQuantity())));
            return i;
        }).toList();
        purchaseOrderService.createOrder(order, orderItems);
        return Result.success();
    }

    @Operation(summary = "提交订单")
    @PutMapping("/submit/{id}")
    @PreAuthorize("hasAuthority('purchase:order:edit')")
    public Result<Void> submit(@PathVariable Long id) { purchaseOrderService.submitOrder(id); return Result.success(); }

    @Operation(summary = "审核订单")
    @PutMapping("/approve/{id}")
    @PreAuthorize("hasAuthority('purchase:order:edit')")
    public Result<Void> approve(@PathVariable Long id) { purchaseOrderService.approveOrder(id); return Result.success(); }

    @Operation(summary = "删除订单")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('purchase:order:remove')")
    public Result<Void> remove(@PathVariable Long id) { purchaseOrderService.removeById(id); return Result.success(); }
}
