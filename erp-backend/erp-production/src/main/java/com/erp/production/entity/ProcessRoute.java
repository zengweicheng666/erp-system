package com.erp.production.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
@Data @TableName("pro_process_route")
public class ProcessRoute {
    @TableId(type = IdType.AUTO) private Long processId;
    private String processName; private Long productId; private Integer processOrder;
    private String workCenter; private Integer prepareTime;
    private Integer processTime; private String remark;
    @TableField(fill = FieldFill.INSERT) private LocalDateTime createTime;
}
