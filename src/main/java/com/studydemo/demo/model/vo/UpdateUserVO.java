package com.studydemo.demo.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/29 18:39
 */
@ApiModel("修改用户信息传入VO类")
@Data
public class UpdateUserVO {
    /**
     * 序列化
     */
    private static final long serialVersionUID = 8073662434406951441L;

    @ApiModelProperty(value = "用户ID")
    private String uid;

    @ApiModelProperty(value = "用户密码")
    private String password;
}
