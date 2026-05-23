package com.erp.system.controller;

import com.erp.common.result.Result;
import com.erp.common.result.PageResult;
import com.erp.system.dto.DictTypeQuery;
import com.erp.system.entity.SysDictType;
import com.erp.system.service.SysDictTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "字典类型管理")
@RestController
@RequestMapping("/system/dict/type")
@RequiredArgsConstructor
public class SysDictTypeController {

    private final SysDictTypeService sysDictTypeService;

    @Operation(summary = "分页查询字典类型")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('system:dict:list')")
    public Result<PageResult<SysDictType>> list(DictTypeQuery query) {
        return Result.success(sysDictTypeService.listDictTypes(query));
    }

    @Operation(summary = "获取字典类型详情")
    @GetMapping("/{dictId}")
    @PreAuthorize("hasAuthority('system:dict:query')")
    public Result<SysDictType> get(@PathVariable Long dictId) {
        return Result.success(sysDictTypeService.getById(dictId));
    }

    @Operation(summary = "新增字典类型")
    @PostMapping
    @PreAuthorize("hasAuthority('system:dict:add')")
    public Result<Void> add(@RequestBody SysDictType dictType) {
        sysDictTypeService.createDictType(dictType);
        return Result.success();
    }

    @Operation(summary = "修改字典类型")
    @PutMapping
    @PreAuthorize("hasAuthority('system:dict:edit')")
    public Result<Void> edit(@RequestBody SysDictType dictType) {
        sysDictTypeService.updateDictType(dictType);
        return Result.success();
    }

    @Operation(summary = "删除字典类型")
    @DeleteMapping("/{dictId}")
    @PreAuthorize("hasAuthority('system:dict:remove')")
    public Result<Void> remove(@PathVariable Long dictId) {
        sysDictTypeService.removeById(dictId);
        return Result.success();
    }
}
