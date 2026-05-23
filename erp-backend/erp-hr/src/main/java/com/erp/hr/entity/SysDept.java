package com.erp.hr.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
@Data @TableName("sys_dept")
public class SysDept {
    @TableId(type = IdType.AUTO) private Long deptId;
    private String deptName; private Long parentId; private Integer orderNum;
    private String leader; private String phone; private Integer status;
    @TableField(fill = FieldFill.INSERT) private LocalDateTime createTime;
    @TableField(exist = false) private List<SysDept> children;
}
