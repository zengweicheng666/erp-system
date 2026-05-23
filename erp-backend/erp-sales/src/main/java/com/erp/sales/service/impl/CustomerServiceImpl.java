package com.erp.sales.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.common.result.PageResult;
import com.erp.sales.dto.CustomerQuery;
import com.erp.sales.entity.Customer;
import com.erp.sales.mapper.CustomerMapper;
import com.erp.sales.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
    @Override
    public PageResult<Customer> listCustomers(CustomerQuery query) {
        LambdaQueryWrapper<Customer> w = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(query.getCustomerCode())) w.like(Customer::getCustomerCode, query.getCustomerCode());
        if (StringUtils.hasText(query.getCustomerName())) w.like(Customer::getCustomerName, query.getCustomerName());
        if (query.getStatus() != null) w.eq(Customer::getStatus, query.getStatus());
        w.orderByDesc(Customer::getCreateTime);
        Page<Customer> p = page(new Page<>(query.getPage(), query.getPageSize()), w);
        return PageResult.of(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize());
    }
    @Override public boolean createCustomer(Customer c) { return save(c); }
    @Override public boolean updateCustomer(Customer c) { return updateById(c); }
}
