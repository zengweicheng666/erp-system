package com.erp.crm.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.common.result.PageResult;
import com.erp.crm.entity.CrmLead;
import com.erp.crm.mapper.CrmLeadMapper;
import com.erp.crm.service.CrmLeadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
@Service @RequiredArgsConstructor
public class CrmLeadServiceImpl extends ServiceImpl<CrmLeadMapper, CrmLead> implements CrmLeadService {
    @Override public PageResult<CrmLead> listLeads(String name, Integer status, Long page, Long pageSize) {
        LambdaQueryWrapper<CrmLead> w = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(name)) w.like(CrmLead::getLeadName, name);
        if (status != null) w.eq(CrmLead::getStatus, status);
        w.orderByDesc(CrmLead::getCreateTime);
        Page<CrmLead> p = page(new Page<>(page, pageSize), w);
        return PageResult.of(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize());
    }
}
