package com.erp.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.system.entity.SysDictData;

import java.util.List;

public interface SysDictDataService extends IService<SysDictData> {
    List<SysDictData> getDictDataByType(String dictType);
    boolean createDictData(SysDictData dictData);
    boolean updateDictData(SysDictData dictData);
}
