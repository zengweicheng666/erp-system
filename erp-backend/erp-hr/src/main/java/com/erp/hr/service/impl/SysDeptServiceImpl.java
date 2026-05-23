package com.erp.hr.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.hr.entity.SysDept;
import com.erp.hr.mapper.SysDeptMapper;
import com.erp.hr.service.SysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service @RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
    @Override public List<SysDept> listDepts() { LambdaQueryWrapper<SysDept> w = new LambdaQueryWrapper<>(); w.orderByAsc(SysDept::getOrderNum); return list(w); }
    @Override public List<SysDept> buildTree(List<SysDept> depts) {
        List<SysDept> tree = new ArrayList<>();
        List<SysDept> flat = depts.stream().filter(d -> d.getDeptId() != null).collect(Collectors.toList());
        for (SysDept d : flat) { if (d.getParentId() == null || d.getParentId() == 0) tree.add(findChildren(d, flat)); }
        return tree;
    }
    private SysDept findChildren(SysDept parent, List<SysDept> flat) {
        List<SysDept> children = new ArrayList<>();
        for (SysDept d : flat) { if (parent.getDeptId().equals(d.getParentId())) children.add(findChildren(d, flat)); }
        parent.setChildren(children); return parent;
    }
}
