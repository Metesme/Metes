package com.eternal.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("pin_task")
public class PinTaskEntity {
    @TableField(value="requestId")
    private String requestId;
    @TableField(value="file_name")
    private String  fileName;
    @TableField(value="hash")
    private String  hash;
    @TableField(value="created")
    private String created;
    @TableField(value="end")
    private Long end;
    @TableField(value="task_status")
    private Long taskStatus;
}
