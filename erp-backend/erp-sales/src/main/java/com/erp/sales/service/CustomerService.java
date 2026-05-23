package com.erp.sales.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.common.result.PageResult;
import com.erp.sales.dto.CustomerQuery;
import com.erp.sales.entity.Customer;
public interface CustomerService extends IService<Customer> {
    PageResult<Customer> listCustomers(CustomerQuery query);
    boolean createCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
}
