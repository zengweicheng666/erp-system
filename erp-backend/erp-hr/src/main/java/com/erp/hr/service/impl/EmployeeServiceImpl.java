package com.erp.hr.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.common.result.PageResult;
import com.erp.hr.entity.Employee;
import com.erp.hr.mapper.EmployeeMapper;
import com.erp.hr.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
@Service @RequiredArgsConstructor
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    @Override public PageResult<Employee> listEmployees(String name, Long deptId, Long page, Long pageSize) {
        LambdaQueryWrapper<Employee> w = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(name)) w.like(Employee::getEmployeeName, name);
        if (deptId != null) w.eq(Employee::getDeptId, deptId);
        w.orderByDesc(Employee::getCreateTime);
        Page<Employee> p = page(new Page<>(page, pageSize), w);
        return PageResult.of(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize());
    }
}
