package com.erp.system.controller;

import com.erp.common.result.Result;
import com.erp.system.entity.SysDictData;
import com.erp.system.service.SysDictDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "字典数据管理")
@RestController
@RequestMapping("/system/dict/data")
@RequiredArgsConstructor
public class SysDictDataController {

    private final SysDictDataService sysDictDataService;

    @Operation(summary = "根据字典类型获取数据")
    @GetMapping("/type/{dictType}")
    public Result<List<SysDictData>> getByType(@PathVariable String dictType) {
        return Result.success(sysDictDataService.getDictDataByType(dictType));
    }

    @Operation(summary = "新增字典数据")
    @PostMapping
    @PreAuthorize("hasAuthority('system:dict:add')")
    public Result<Void> add(@RequestBody SysDictData dictData) {
        sysDictDataService.createDictData(dictData);
        return Result.success();
    }

    @Operation(summary = "修改字典数据")
    @PutMapping
    @PreAuthorize("hasAuthority('system:dict:edit')")
    public Result<Void> edit(@RequestBody SysDictData dictData) {
        sysDictDataService.updateDictData(dictData);
        return Result.success();
    }

    @Operation(summary = "删除字典数据")
    @DeleteMapping("/{dictCode}")
    @PreAuthorize("hasAuthority('system:dict:remove')")
    public Result<Void> remove(@PathVariable Long dictCode) {
        sysDictDataService.removeById(dictCode);
        return Result.success();
    }
}
