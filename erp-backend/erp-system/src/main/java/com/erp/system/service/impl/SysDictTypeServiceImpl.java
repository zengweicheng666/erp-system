package com.erp.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.common.result.PageResult;
import com.erp.system.dto.DictTypeQuery;
import com.erp.system.entity.SysDictType;
import com.erp.system.mapper.SysDictTypeMapper;
import com.erp.system.service.SysDictTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    @Override
    public PageResult<SysDictType> listDictTypes(DictTypeQuery query) {
        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(query.getDictName())) {
            wrapper.like(SysDictType::getDictName, query.getDictName());
        }
        if (StringUtils.hasText(query.getDictType())) {
            wrapper.like(SysDictType::getDictType, query.getDictType());
        }
        if (query.getStatus() != null) {
            wrapper.eq(SysDictType::getStatus, query.getStatus());
        }

        Page<SysDictType> page = new Page<>(query.getPage(), query.getPageSize());
        Page<SysDictType> result = page(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @Override
    public boolean createDictType(SysDictType dictType) {
        return save(dictType);
    }

    @Override
    public boolean updateDictType(SysDictType dictType) {
        return updateById(dictType);
    }
}
