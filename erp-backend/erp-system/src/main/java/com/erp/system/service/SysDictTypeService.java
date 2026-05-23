package com.erp.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.system.dto.DictTypeQuery;
import com.erp.system.entity.SysDictType;
import com.erp.common.result.PageResult;

public interface SysDictTypeService extends IService<SysDictType> {
    PageResult<SysDictType> listDictTypes(DictTypeQuery query);
    boolean createDictType(SysDictType dictType);
    boolean updateDictType(SysDictType dictType);
}
