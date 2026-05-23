package com.erp.sales.controller;
import com.erp.common.result.Result;
import com.erp.common.result.PageResult;
import com.erp.sales.dto.SalesOrderQuery;
import com.erp.sales.entity.SalesOrder;
import com.erp.sales.entity.SalesOrderItem;
import com.erp.sales.service.SalesOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Tag(name = "销售订单管理")
@RestController
@RequestMapping("/sales/order")
@RequiredArgsConstructor
public class SalesOrderController {
    private final SalesOrderService salesOrderService;

    @Operation(summary = "分页查询销售订单")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sales:order:list')")
    public Result<PageResult<SalesOrder>> list(SalesOrderQuery q) { return Result.success(salesOrderService.listOrders(q)); }

    @GetMapping("/{id}")
    public Result<SalesOrder> get(@PathVariable Long id) { return Result.success(salesOrderService.getOrderWithItems(id)); }

    @PostMapping
    @PreAuthorize("hasAuthority('sales:order:add')")
    public Result<Void> add(@RequestBody Map<String, Object> params) {
        SalesOrder order = new SalesOrder();
        order.setCustomerId(Long.valueOf(params.get("customerId").toString()));
        order.setOrderDate(java.time.LocalDateTime.now());
        order.setRemark((String) params.get("remark"));
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> items = (List<Map<String, Object>>) params.get("items");
        List<SalesOrderItem> orderItems = items.stream().map(m -> {
            SalesOrderItem i = new SalesOrderItem();
            i.setProductId(Long.valueOf(m.get("productId").toString()));
            i.setQuantity(Integer.valueOf(m.get("quantity").toString()));
            i.setUnitPrice(new java.math.BigDecimal(m.get("unitPrice").toString()));
            i.setTotalPrice(i.getUnitPrice().multiply(java.math.BigDecimal.valueOf(i.getQuantity())));
            return i;
        }).toList();
        salesOrderService.createOrder(order, orderItems);
        return Result.success();
    }

    @PutMapping("/submit/{id}")
    @PreAuthorize("hasAuthority('sales:order:edit')")
    public Result<Void> submit(@PathVariable Long id) { salesOrderService.submitOrder(id); return Result.success(); }

    @PutMapping("/approve/{id}")
    @PreAuthorize("hasAuthority('sales:order:edit')")
    public Result<Void> approve(@PathVariable Long id) { salesOrderService.approveOrder(id); return Result.success(); }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sales:order:remove')")
    public Result<Void> remove(@PathVariable Long id) { salesOrderService.removeById(id); return Result.success(); }
}
