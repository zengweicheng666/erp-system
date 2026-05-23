package com.erp.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.system.entity.SysDictData;
import com.erp.system.mapper.SysDictDataMapper;
import com.erp.system.service.SysDictDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    @Override
    public List<SysDictData> getDictDataByType(String dictType) {
        LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictData::getDictType, dictType);
        wrapper.eq(SysDictData::getStatus, 0);
        wrapper.orderByAsc(SysDictData::getDictSort);
        return list(wrapper);
    }

    @Override
    public boolean createDictData(SysDictData dictData) {
        return save(dictData);
    }

    @Override
    public boolean updateDictData(SysDictData dictData) {
        return updateById(dictData);
    }
}
