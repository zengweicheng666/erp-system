package com.erp.crm.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.common.result.PageResult;
import com.erp.crm.entity.CrmOpportunity;
import com.erp.crm.mapper.CrmOpportunityMapper;
import com.erp.crm.service.CrmOpportunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class CrmOpportunityServiceImpl extends ServiceImpl<CrmOpportunityMapper, CrmOpportunity> implements CrmOpportunityService {
    @Override public PageResult<CrmOpportunity> listOpportunities(Long customerId, Long page, Long pageSize) {
        LambdaQueryWrapper<CrmOpportunity> w = new LambdaQueryWrapper<>();
        if (customerId != null) w.eq(CrmOpportunity::getCustomerId, customerId);
        w.orderByDesc(CrmOpportunity::getCreateTime);
        Page<CrmOpportunity> p = page(new Page<>(page, pageSize), w);
        return PageResult.of(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize());
    }
}
