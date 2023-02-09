package com.studydemo.demo.model.entity;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 系统日志
 * @Author teronb
 * @Date 2023/2/3 22:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class SysLog {
    private static final long serialVersionUID=1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty("操作人员")
    @TableField(value = "operation_user")
    private String operationUser;

    @ApiModelProperty("操作方法路径")
    @TableField(value = "path")
    private String path;

    @ApiModelProperty("操作时间")
    @TableField(value = "time")
    private String time;

    @ApiModelProperty("持续时间")
    @TableField(value = "duration")
    private String duration;

    @ApiModelProperty("操作方法参数")
    @TableField(value = "parameter")
    private String parameter;

    @ApiModelProperty("操作方法")
    @TableField(value = "title")
    private String title;

    @ApiModelProperty("方法描述")
    @TableField(value = "action")
    private String action;

    @ApiModelProperty("系统类型")
    @TableField(value = "sys_type")
    private String sysType;

    @ApiModelProperty("操作类型")
    @TableField(value = "op_type")
    private String opType;

    public SysLog(String operationUser, String path, String time,String duration,
                  String parameter, String title, String action, String sysType, String opType) {
        super();
        this.operationUser = operationUser;
        this.path = path;
        this.time = time;
        this.duration = duration;
        this.parameter = parameter;
        this.title = title;
        this.action = action;
        this.sysType = sysType;
        this.opType = opType;
    }

}
