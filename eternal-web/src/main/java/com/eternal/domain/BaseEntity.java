package com.eternal.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author Eternal
 * @version 1.0
 * @description: TODO
 * @date 2021/9/23 5:28 下午
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @TableId(type=IdType.AUTO)
    private Long id;
    /**
     * 删除标识
     */
    @TableField(exist= false)
    private String delFlag;
    /**
     * 搜索值
     */
    @TableField(exist= false)
    private String searchValue;

    /**
     * 创建者
     */

    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value="create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(exist= false)
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(exist= false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 备注
     */
    @TableField(exist= false)
    private String remark;

    /**
     * 请求参数
     */
    @TableField(exist= false)
    private Map<String, Object> params;
}
