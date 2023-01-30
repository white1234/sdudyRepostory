package com.studydemo.demo.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/30 0:48
 */

@Data
@ApiModel("用户详情BO类")
public class UserLoginBO {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 8073662434406951441L;

    @ApiModelProperty(value = "用户信息")
    private UserDetailBO userDetailBO;

    @ApiModelProperty(value = "token")
    private String token;

//    @ApiModelProperty(value = "token时效")
//    private Date tokenTime;
}
