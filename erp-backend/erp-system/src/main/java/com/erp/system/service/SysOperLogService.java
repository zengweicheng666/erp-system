package com.erp.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.system.dto.OperLogQuery;
import com.erp.system.entity.SysOperLog;
import com.erp.common.result.PageResult;

public interface SysOperLogService extends IService<SysOperLog> {
    PageResult<SysOperLog> listLogs(OperLogQuery query);
}
